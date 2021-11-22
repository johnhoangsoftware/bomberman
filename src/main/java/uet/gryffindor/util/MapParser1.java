package uet.gryffindor.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class MapParser1 {
    public static Map parse(InputStream config) {
        try (Scanner sc = new Scanner(config)) {
            int level = sc.nextInt();
            int height = sc.nextInt();
            int width = sc.nextInt();

            int[][] rawMap = new int[height][width];
            SortedList<GameObject> objects = new SortedList<>();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Vector2D position = new Vector2D(j, i);

                    String tokens = sc.next();

                    for (GameObject obj : decodeTokens(tokens)) {
                        obj.position = position.multiply(Sprite.DEFAULT_SIZE);
                        objects.add(obj);
                    }
                }
            }

            return new Map(rawMap, objects, level);
        }
    }

    private static List<GameObject> decodeTokens(String tokens) {
        List<GameObject> objects = new ArrayList<>();
        for (String token : tokens.split("-")) {
            char symbol = token.charAt(0);
            int type = Integer.parseInt(token.substring(1));

            switch (symbol) {
            case 'w':
                Wall wall = new Wall();
                wall.setTexture(new SpriteTexture(Sprite.tiles[type], wall));
                objects.add(wall);
                break;
            case 'o':
                Brick obstacle = new Brick();
                obstacle.setTexture(new SpriteTexture(Sprite.obstacle[type], obstacle));
                objects.add(obstacle);
                break;
            case 'f':
                Floor floor = new Floor();
                floor.setTexture(new SpriteTexture(Sprite.tiles[type], floor));
                objects.add(floor);
                break;
            case 'p':
                Bomber bomber = new Bomber();
                objects.add(bomber);
                break;
            case 'e':
                Enemy enemy = new Enemy();
                if (type == 0) {
                    enemy.getTexture().changeTo("balloom");
                } else if (type == 1) {
                    enemy.getTexture().changeTo("oneal");
                }
                objects.add(enemy);
                break;
            default:
                break;
            }
        }

        return objects;
    }
}