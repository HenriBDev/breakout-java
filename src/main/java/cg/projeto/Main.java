package cg.projeto;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import cg.projeto.Events.Keyboard;
import cg.projeto.Events.Mouse;
import cg.projeto.UI.Tela;

public class Main {

    public static boolean DEBUG = false;
    
    public static GLWindow window = null;

    //Cria a janela de rendeziração do JOGL
    public static void init(){

        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setBackgroundOpaque(true);
        window = GLWindow.create(caps);
        
        // Adiciona a Cena na janela e os inputs
        window.addGLEventListener(new Tela()); 
        window.addKeyListener(new Keyboard());
        window.addMouseListener(new Mouse());
        
        // window.requestFocus();x
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start(); //inicia o loop de animação
        
        //encerrar a aplicacao adequadamente
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });       
        
        window.setTitle("Pong maneiro fi");
        window.setFullscreen(true);
        window.setPointerVisible(false);
        window.setVisible(true);

    }
  
    public static void main(String[] args) {
        init();
    }

}
