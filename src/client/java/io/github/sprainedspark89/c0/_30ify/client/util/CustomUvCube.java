
package io.github.sprainedspark89.c0._30ify.client.util;

import java.util.EnumSet;
import java.util.Map;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;

/**
 * Creates a normal ModelPart.Cube with explicitly assigned UV rectangles.
 *
 * UV coordinates are in texture pixels, not normalized coordinates.
 */
public final class CustomUvCube {
	private static final float TEXTURE_WIDTH = 64.0F;
	private static final float TEXTURE_HEIGHT = 64.0F;

	private CustomUvCube() {
	}

	/**
	 * The UV rectangle for one face. Coordinates are deliberately not reordered:
	 * reversed U or V values can be used to preserve the orientation of a texture.
	 */
	public record FaceUV(float u0, float v0, float u1, float v1) {
	}

	/**
	 * Creates a cube with custom UVs for all six faces.
	 *
	 * @param x       minimum X coordinate
	 * @param y       minimum Y coordinate
	 * @param z       minimum Z coordinate
	 * @param width   cube width
	 * @param height  cube height
	 * @param depth   cube depth
	 * @param faceUVs UV rectangle for every Direction
	 */
	public static ModelPart.Cube create(float x, float y, float z, float width, float height, float depth,
			Map<Direction, FaceUV> faceUVs) {
		for (Direction direction : Direction.values()) {
			if (!faceUVs.containsKey(direction)) {
				throw new IllegalArgumentException("Missing UV rectangle for face: " + direction);
			}
		}

		// Let Minecraft generate the cube's geometry and face vertices.
		ModelPart.Cube cube = new ModelPart.Cube(0, 0, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, false,
				TEXTURE_WIDTH, TEXTURE_HEIGHT, EnumSet.allOf(Direction.class));

		// ModelPart.Cube creates polygons in this exact order.
		Direction[] faceOrder = { Direction.DOWN, Direction.UP, Direction.WEST, Direction.NORTH, Direction.EAST,
				Direction.SOUTH };

		for (int i = 0; i < faceOrder.length; i++) {
			Direction direction = faceOrder[i];
			FaceUV uv = faceUVs.get(direction);

			// Clone the array so the new polygon can remap its vertices
			// without modifying the original polygon's vertex array.
			ModelPart.Vertex[] vertices = cube.polygons[i].vertices().clone();

			cube.polygons[i] = new ModelPart.Polygon(vertices, uv.u0(), uv.v0(), uv.u1(), uv.v1(), TEXTURE_WIDTH,
					TEXTURE_HEIGHT, false, direction);
		}

		return cube;
	}
}
