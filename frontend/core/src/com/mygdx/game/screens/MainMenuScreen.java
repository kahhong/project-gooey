package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.*;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainMenuScreen extends ScreenAdapter{

    final Boot game;
    private OrthographicCamera camera;

	private Stage stage;
    private TextField textField;

    private Label valueLabel, nameLabel;

    private Slider slider;

    private boolean proceed = false;

    public MainMenuScreen(Boot boot){
        this.game = boot;
        this.camera = boot.camera;
    }

	public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // You can use a different skin
        // Create a text field
        TextFieldStyle textFieldStyle = skin.get(TextFieldStyle.class);
        textField = new TextField("", skin);


        // Label
        nameLabel = new Label("Username", skin);
        valueLabel = new Label("Number of Players: 1", skin);

        // Create a Slider
        //Slider.SliderStyle sliderStye = skin.get(Slider.SliderStyle.class);
        slider = new Slider(1, 6, 1, false, skin);

        // Add a listener to respond to changes in slider value
        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                // Handle slider value change
                valueLabel.setText(String.format("Number of Players: %d", (int)slider.getValue()));
            }
        });

        //TextButton
        TextButton btnStart = new TextButton("Start Game", skin);
        //btnStart.setSize(200, 100);
        btnStart.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                // Handle slider value change
                proceed = true;
            }
        });

        // Add text field to a table
        Table table = new Table();
        table.setFillParent(true);
        table.add(nameLabel).top().left();
        table.row();
        table.add().padTop(20);
        table.row();
        table.add(textField).width(300).height(40);
        table.row();
        table.add().padTop(50);
        table.row();
        table.add(valueLabel).top().left();
        table.row();
        table.add(slider).width(300).height(50).left();
        table.row();
        table.add().padTop(20);
        table.row();
        table.add(btnStart).width(250).height(50);
        stage.addActor(table);

    }

    @Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
        stage.act(delta);
        stage.draw();
        game.batch.end();

		if (proceed) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

	@Override
    public void dispose() {
        stage.dispose();
    }
}
