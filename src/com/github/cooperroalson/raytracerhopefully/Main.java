package com.github.cooperroalson.raytracerhopefully;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.cooperroalson.raytracerhopefully.geometry.MatrixTransform3d;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.Camera;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraData;
import com.github.cooperroalson.raytracerhopefully.scene.camera.PinholeCamera;
import com.github.cooperroalson.raytracerhopefully.scene.lights.DirectionalLight;
import com.github.cooperroalson.raytracerhopefully.scene.lights.SceneLight;
import com.github.cooperroalson.raytracerhopefully.scene.objects.Disk;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;
import com.github.cooperroalson.raytracerhopefully.scene.objects.Sphere;
import com.github.cooperroalson.raytracerhopefully.scene.objects.TestFloorObject;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.DiffuseShader;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.ReflectionShader;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.TransparentShader;

public class Main implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	public static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;
	public static final int TARGET_FPS = 7;
	public static final double MILLIS_PER_FRAME = 1000/TARGET_FPS;
	
	public static final int WIDTH = 300, HEIGHT = 300;
	public static final int FOV = 40;

	private int renderDepth = 4;
	
	public static final double MOVEMENT_SPEED = 8;
	public static final double MOVEMENT_PER_FRAME = MOVEMENT_SPEED * MILLIS_PER_FRAME / 1000;
	public static final double DRAG_SENSITIVITY = 0.75;
	
	private Set<SceneObject> objects;
	private Set<SceneLight> lights;
	private Camera camera;
	private Scene scene;
	
	JFrame frame;
	JPanel panel;
		
	public static void main(String[] args) {
		new Thread(new Main()).start();
	}
	
	public void mainloop() {
		boolean running = true;
		
		long lastUpdate = System.currentTimeMillis();
		
		while (running) {
			update();
			frame.repaint();
			while (System.currentTimeMillis() - lastUpdate < MILLIS_PER_FRAME) {
				try {Thread.sleep(1);} catch (InterruptedException e) {}
			}
			lastUpdate += MILLIS_PER_FRAME;
		}
	}
	
	public void update() {
		
		// *----- Movement -----*
		Vector3f dir = camera.getViewDirection();
		dir.y = 0;
		dir = dir.normalized();
		
		int ud = getDir(up, down);
		camera.transformAbsolute(new MatrixTransform3d().translate(0, ud * MOVEMENT_PER_FRAME, 0));
		int lr = getDir(left, right);
		camera.transformAbsolute(new MatrixTransform3d().translate(lr * -dir.z*MOVEMENT_PER_FRAME, 0, lr * dir.x*MOVEMENT_PER_FRAME));
		int fb = getDir(forward, backward);
		camera.transformAbsolute(new MatrixTransform3d().translate(fb * dir.x*MOVEMENT_PER_FRAME, 0, fb * dir.z*MOVEMENT_PER_FRAME));
	
		// *----- Turning -----*
		
		if (dragging) {
			double azimuthAngle = DRAG_SENSITIVITY * ((double) (mouseDragX - mouseDragStartX)/WINDOW_WIDTH) * (2 * Math.PI);
			Vector3f pos = camera.getPos();
			
			camera.transformAbsolute(new MatrixTransform3d().translate(pos).rotateYaw(azimuthAngle).translate(pos.times(-1)));
			
			
			double polarAngle = -DRAG_SENSITIVITY * ((double) (mouseDragY - mouseDragStartY)/WINDOW_HEIGHT) * Math.PI;
			double currentAngle = Math.asin(camera.getViewDirection().y);
			
			if (polarAngle + currentAngle < Math.PI/2 && polarAngle + currentAngle > -Math.PI/2) {
				camera.transformRelative(new MatrixTransform3d().rotatePitch(polarAngle));
			}
		}
		
		mouseDragStartX = mouseDragX;
		mouseDragStartY = mouseDragY;
	}
	
	@SuppressWarnings("serial")
	@Override
	public void run() {
		// *----- Objects -----*
		objects = new HashSet<>();
		
		objects.add(new TestFloorObject(-5, new Color(255, 0, 255)));
		
		objects.add(new Sphere(new Vector3f(0, 0, -6), 3, new DiffuseShader(new Color(255, 0, 0))));
		objects.add(new Sphere(new Vector3f(6, 1, -8), 3, new TransparentShader(1.5, new Color(160, 160, 255))));
		objects.add(new Sphere(new Vector3f(4, 7, -1), 1.5, new DiffuseShader(new Color(0, 255, 0))));
		
		objects.add(new Disk(new Vector3f(-3, -2, -14), 10, new Vector3f(0.75, 0.3, 1), new ReflectionShader(new Color(200, 200, 200))));
		
		// *----- Lights -----*
		lights = new HashSet<>();
			
		lights.add(new DirectionalLight(new Vector3f(-1, -1.5, -1).normalized(), new Color(750,750,750)));
		
		// *----- Camera -----*
		camera = new PinholeCamera(new CameraData()
			.setImageSize(WIDTH, HEIGHT)
			.setFocalLength(1)
			.setFOVDegrees(FOV)
			.lookAt(new Vector3f(0, 5, 2), new Vector3f(0, 0, -6))
		);
		
		// *----- Scene -----*
		scene = new Scene(objects, lights, camera, new Color(63, 63, 255));
		
		// ----------
		
		/*try {
			String filename = "/Users/CooperRoalson/Downloads/render.png";
			
			ImageIO.write(this.render(), "png", new File(filename));
			System.out.println("Successfully wrote to file: " + filename);
		
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		// ----------
		
		frame = new JFrame("Ray Tracer Hopefully");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        panel = new JPanel() {
        	@Override
    		public void paintComponent(Graphics g) {
    			super.paintComponent(g);
    			((Graphics2D) g).scale((double) Main.WINDOW_WIDTH/Main.WIDTH, (double) Main.WINDOW_HEIGHT/Main.HEIGHT);
    			g.drawImage(render(), 0, 0, null);
    		}
        };
        
		panel.setBackground(new java.awt.Color(0, 0, 0));
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        panel.addKeyListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        
        frame.add(panel, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        panel.requestFocusInWindow();
        
        mainloop();
	}
	
	public BufferedImage render() {
		return this.scene.render(renderDepth);
	}
	
	private boolean forward, backward, left, right, up, down;
	
	private static int getDir(boolean plus, boolean minus) {
		return plus ? (minus ? 0 : 1) : (minus ? -1 : 0);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
				
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_SPACE:
			up = true;
			break;
		case KeyEvent.VK_SHIFT:
			down = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_S:
			backward = true;
			break;
		case KeyEvent.VK_W:
			forward = true;
			break;
			
		case KeyEvent.VK_EQUALS:
			this.renderDepth ++;
			break;
		case KeyEvent.VK_MINUS:
			this.renderDepth = Math.max(1, renderDepth-1);
			break;
			
		default:
			return;

		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
				
		case KeyEvent.VK_SPACE:
			up = false;
			break;
		case KeyEvent.VK_SHIFT:
			down = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_S:
			backward = false;
			break;
		case KeyEvent.VK_W:
			forward = false;
			break;
		
		default:
			return;
			
		}
	}
	
	
	private int mouseDragStartX, mouseDragStartY;
	private int mouseDragX, mouseDragY;
	private boolean dragging = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		if (!dragging) {
			dragging = true;
			
			mouseDragX = e.getX();
			mouseDragY = e.getY();
			mouseDragStartX = mouseDragX;
			mouseDragStartY = mouseDragY;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			this.dragging = false;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseDragX = e.getX();
			mouseDragY = e.getY();
		}
	}

}
