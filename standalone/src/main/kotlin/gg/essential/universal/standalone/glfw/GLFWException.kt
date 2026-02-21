
package gg.essential.universal.standalone.glfw

@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class GLFWException(
    val errorMessage: String,
    val glfwErrorMessage: String?,
    val glfwErrorCode: Int?,
) : RuntimeException("$errorMessage: $glfwErrorMessage${if (glfwErrorCode != null) " (code $glfwErrorCode)" else ""}")
