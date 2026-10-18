package io.github.sprainedspark89.c0._30ify.client.util;

import org.joml.Vector3fc;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.geometry.BakedQuad;

public class InternerImpl implements ModelBaker.Interner {
		private final Interner<Vector3fc> vectors = Interners.newStrongInterner();
		private final Interner<BakedQuad.MaterialInfo> materialInfos = Interners.newStrongInterner();

		@Override
		public Vector3fc vector(final Vector3fc v) {
			return this.vectors.intern(v);
		}

		@Override
		public BakedQuad.MaterialInfo materialInfo(final BakedQuad.MaterialInfo material) {
			return this.materialInfos.intern(material);
		}
	}