package nottanengine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        // Private so that only one Window class exists.
        this.width = 1920;
        this.height = 1080;
        this.title = "ItsAMeMario";
    }

    public static Window get() {
        // If the window does not exist, it creates one.
        if (Window.window == null) {
            System.out.println("Creating a new Window.");
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        System.out.println("Freeing memory.");
        // Free Memory once the loop has exited.
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        System.out.println("Terminating GLFW and freeing the error callbacks.");
        // Terminate GLFW and free the error callbacks.
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback.
        // An error callback defines where GLFW prints errors to.
        // We will set it to build log.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will open maximized.

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Setup Callbacks.
        // :: means lambda functions.

        // Mouse Callbacks.
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        // Key Callbacks
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current.
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync.
        glfwSwapInterval(1);

        // Make the window visible.
        glfwShowWindow(glfwWindow);

        // Makes the bindings available for use.
        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events.
            glfwPollEvents();

            glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
