package uet.gryffindor.game.object.statics.items;

import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class BombItem extends Item {

  @Override
  public void start() {
    super.start();
    double rate = 1;
    animator = new Animator(rate, Sprite.explosionPotion);
    effectDuration = 100;

  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      Bomber bomber = (Bomber) that.gameObject;
      int backupBombCount = bomber.getBombCount();
      this.destroy();

      TimeCounter.callAfter(() -> bomber.setBombsCount(backupBombCount),
          effectDuration, TimeUnit.SECONDS);
    }
  }
}
