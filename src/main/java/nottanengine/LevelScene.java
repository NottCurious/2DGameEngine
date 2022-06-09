package nottanengine;

public class LevelScene extends Scene {
    public LevelScene() {
        System.out.println("Inside LevelScene.");
        Window.get().r = 0.5f;
        Window.get().g = 0.5f;
        Window.get().b = 0.5f;
    }

    @Override
    public void update(float dt) {

    }
}
