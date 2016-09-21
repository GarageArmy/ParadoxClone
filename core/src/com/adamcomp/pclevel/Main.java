package com.adamcomp.pclevel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends Game {
    private XmlReader.Element dom; //BTW Stands for Document Object Model

    private Stage stage;
    private Skin skin;
    private Window wnd;

    @Override
    public void create () {
        int load = JOptionPane.showConfirmDialog(null, "Would you like to open an existing level?",
                "Load level", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(load == JOptionPane.YES_OPTION) load();
        else dom = new XmlReader().parse("<xml><level></level></xml>");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("visui/uiskin.json"),
                new TextureAtlas("visui/uiskin.atlas"));

        wnd = new Window("Objects", skin);
        stage.addActor(wnd);
        wnd.debug();
        wnd.setPosition(10, 10);
        wnd.setSize(150, 500);
        TextButton btn = new TextButton("TEST", skin);
        wnd.add().setActor(btn);
    }

    private void load()
    {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file", "xml");
        chooser.setFileFilter(filter);

        XmlReader reader = new XmlReader();
        while(true){
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    //FIXME Gets error on valid XML files
                    dom = reader.parse(new FileHandle(chooser.getSelectedFile()));
                    return;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
            else break;
        }
        dom = new XmlReader().parse("<xml><level></level></xml>");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }
}