package grafika.zad2.filtry;

import grafika.zad2.ImageWindow;

public class BrightnessChangerFilter extends FilterWindow{

    public BrightnessChangerFilter(ImageWindow imageWindow) {
        super("Brightness Changer", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }
    
    private void addComponents() {
        
    }

    @Override
    public void closeMe() {
        getWindow().setBrightnessChangerWindow(null);
    }
    
}
