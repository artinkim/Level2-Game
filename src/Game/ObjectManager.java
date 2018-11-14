package Game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextPane;

public class ObjectManager {
	ArrayList<Platform> plats = new ArrayList<Platform>();
	ArrayList<CannonProjectile> projectiles = new ArrayList<CannonProjectile>();
	ArrayList<Coin> coins = new ArrayList<Coin>();
	ArrayList<floor> floors = new ArrayList<floor>();
	ArrayList<TimePowerUp> TPowers = new ArrayList<TimePowerUp>();
	Player p;
	long T;
	Random rand = new Random();
	long enemyTimer = -5000;
	long enemyTimer2 = 0;
	long enemyTimer3 = -5000;
	long enemyTimer4 = 0;
	long enemyTimer5 = -5000;

	int enemySpawnTime = 1000;
	int floorSpawnTime = 1500;
	int projectileSpawnTime = 2000;
	int score = 0;
	int speed = 1;

	floor f = new floor(0, 600, 1400, 25);

	ObjectManager(Player a) {
		p = a;
	}

	void makePlats() {
		if (System.currentTimeMillis() * speed - enemyTimer >= enemySpawnTime) {
			plats.add(new Platform(1400, rand.nextInt(400) + 100, rand.nextInt(40) + 30, 5));
			enemyTimer = System.currentTimeMillis();
		}

	}

	void makePowerUps() {
		if (System.currentTimeMillis() * speed - enemyTimer5 >= rand.nextInt(15000) + 15000) {
			TPowers.add(new TimePowerUp(0, 0, 20, 20));
			enemyTimer5 = System.currentTimeMillis();
		}

	}

	void makeFloor() {
		if (System.currentTimeMillis() * speed - enemyTimer4 >= rand.nextInt(2000) + 8000) {
			floors.add(new floor(1300, 700, 50, 25));
			enemyTimer4 = System.currentTimeMillis();
		}
	}

	void makeProjectilesR() {

		if (System.currentTimeMillis() * speed - enemyTimer2 >= projectileSpawnTime) {
			projectiles.add(new CannonProjectile(1400, 0, 20, 20, p.x, p.y));
			enemyTimer2 = System.currentTimeMillis();
		}

	}

	void makeCoins() {
		if (System.currentTimeMillis() - enemyTimer3 >= rand.nextInt(2000) + 4000) {
			coins.add(new Coin(0, 0, 20, 20));
			enemyTimer3 = System.currentTimeMillis();
		}

	}

	void draw(Graphics g) {
		f.draw(g);
		for (Platform n : plats) {
			n.draw(g);
		}
		for (CannonProjectile n : projectiles) {
			n.draw(g);
		}
		for (Coin n : coins) {
			n.draw(g);
		}
		for (floor n : floors) {
			n.draw(g);
		}
		for (TimePowerUp n : TPowers) {
			n.draw(g);
		}
	}

	void update() {
		f.update();
		f.speed = speed;
		if (f.collisionBox.intersects(p.collisionBox)) {
			p.floor(f);
		}

		for (int j = 0; j < TPowers.size(); j++) {
			TPowers.get(j).speed = speed;
			TPowers.get(j).update();
			if (TPowers.get(j).collisionBox.intersects(p.collisionBox)) {
				T = System.currentTimeMillis();
				speed = 0;
				TPowers.get(j).speed = speed;
				TPowers.remove(TPowers.get(j));
			}

		}

		if (System.currentTimeMillis() - 5000 >= T) { // For time power up length
			speed = 1;
		}

		for (Platform n : plats) {
			n.speed = speed;
			n.update();
			if (n.collisionBox.intersects(p.collisionBox)) {
				p.floor(n);
			}
		}

		for (int j = 0; j < floors.size(); j++) {
			floors.get(j).speed = speed;
			floors.get(j).update();
			if (floors.get(j).collisionBox.intersects(p.collisionBox)) {
				p.y = 0;
				p.velocity = 0;
				floors.remove(floors.get(j));
			}
		}

		for (int j = 0; j < projectiles.size(); j++) {
			projectiles.get(j).speed = speed;
			projectiles.get(j).update();
			if (projectiles.get(j).collisionBox.intersects(p.collisionBox)) {
				p.lives--;
				projectiles.remove(projectiles.get(j));

			}
		}
		for (int j = 0; j < coins.size(); j++) {
			coins.get(j).speed = speed;
			coins.get(j).update();
			if (coins.get(j).collisionBox.intersects(p.collisionBox)) {
				score++;
				if (projectileSpawnTime <= 400) {
					projectileSpawnTime = 400;
				} else {
					projectileSpawnTime -= 50;
				}
				coins.remove(coins.get(j));
			}

		}
	}
}
