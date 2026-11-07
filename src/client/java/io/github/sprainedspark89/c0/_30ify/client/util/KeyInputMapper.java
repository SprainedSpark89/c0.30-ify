//package io.github.sprainedspark89.c0._30ify.client.util;
//
//import org.lwjgl.glfw.GLFW;
//
//import net.minecraft.client.input.KeyEvent;
//
//public class KeyInputMapper { // Nostalgia-trip, one of many mods i made but never finished
//
//    public static Character toChar(KeyEvent input) {
//        int key = input.getDigit();
//        boolean shift = input.hasShiftDown();
//
//        // A–Z
//        if (key >= GLFW.GLFW_KEY_A && key <= GLFW.GLFW_KEY_Z) {
//            char c = (char) ('a' + (key - GLFW.GLFW_KEY_A));
//            return shift ? Character.toUpperCase(c) : c;
//        }
//        if(key == GLFW.GLFW_KEY_ESCAPE) {
//        	return 1;
//        }
//
//        if(key == GLFW.GLFW_KEY_BACKSPACE) {
//        	return 14;
//        }
//
//        if(key == GLFW.GLFW_KEY_ENTER) {
//        	return 28;
//        }
//        // Number row
//        return switch (key) {
//            case GLFW.GLFW_KEY_1 -> shift ? '!' : '1';
//            case GLFW.GLFW_KEY_2 -> shift ? '@' : '2';
//            case GLFW.GLFW_KEY_3 -> shift ? '#' : '3';
//            case GLFW.GLFW_KEY_4 -> shift ? '$' : '4';
//            case GLFW.GLFW_KEY_5 -> shift ? '%' : '5';
//            case GLFW.GLFW_KEY_6 -> shift ? '^' : '6';
//            case GLFW.GLFW_KEY_7 -> shift ? '&' : '7';
//            case GLFW.GLFW_KEY_8 -> shift ? '*' : '8';
//            case GLFW.GLFW_KEY_9 -> shift ? '(' : '9';
//            case GLFW.GLFW_KEY_0 -> shift ? ')' : '0';
//
//            case GLFW.GLFW_KEY_SPACE -> ' ';
//            case GLFW.GLFW_KEY_MINUS -> shift ? '_' : '-';
//            case GLFW.GLFW_KEY_EQUAL -> shift ? '+' : '=';
//            case GLFW.GLFW_KEY_LEFT_BRACKET -> shift ? '{' : '[';
//            case GLFW.GLFW_KEY_RIGHT_BRACKET -> shift ? '}' : ']';
//            case GLFW.GLFW_KEY_BACKSLASH -> shift ? '|' : '\\';
//            case GLFW.GLFW_KEY_SEMICOLON -> shift ? ':' : ';';
//            case GLFW.GLFW_KEY_APOSTROPHE -> shift ? '"' : '\'';
//            case GLFW.GLFW_KEY_COMMA -> shift ? '<' : ',';
//            case GLFW.GLFW_KEY_PERIOD -> shift ? '>' : '.';
//            case GLFW.GLFW_KEY_SLASH -> shift ? '?' : '/';
//
//            default -> null;
//        };
//    }
//}
//
