package RobotExam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RobotMain {
	public static void main(String[] arg) {
		Robot robot = new Robot();
		// Scanner command = null;
		BufferedReader command = null;
		try {
			while (true) {
				// Enter data using BufferReader
				command = new BufferedReader(new InputStreamReader(System.in));
				// check string empty
				if (command.readLine() == null) {
					continue;
				}
				// Reading data using readLine
				String[] cmd = command.readLine().split("\\s+");
				// Convert character to upper case
				cmd[0] = cmd[0].toUpperCase();
				// check command what input from screen
				if (cmd[0].equals("PLACE")) {
					// check PLACE command format
					if (cmd.length < 2) {
						System.out.println("Incorrect command.");
						continue;
					}
					// split text with comma
					String[] propertys = cmd[1].split(",");
					robot = new Robot(Integer.parseInt(propertys[0]), Integer.parseInt(propertys[1]), propertys[2]);
				}

				// ignore all command when a robot is place not on the table
				if ((robot.getX() < 0 || robot.getX() > 5) || (robot.getY() < 0 || robot.getY() > 5)) {
					System.out.println("Please place a robot on table.(size 5*5)");
					continue;
				}

				if (cmd[0].equals("MOVE")) {
					robot.move();
				} else if (cmd[0].equals("REPORT")) {
					System.out.println(String.format("Output: %d,%d,%s", robot.getX(), robot.getY(), robot.getF()));
					break;
				} else if (cmd[0].equals("LEFT") || cmd[0].equals("RIGHT")) {
					robot.rotate(cmd[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				command.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Robot {
	/**************
	 * Property
	 **************/
	private int x, y;
	private Direction f;

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getF() {
		return this.f;
	}

	public void setF(Direction f) {
		this.f = f;
	}

	/***************
	 * Constructor
	 **************/
	public Robot() {

	}

	// overload constructor
	public Robot(int x, int y, String f) {
		this.x = x;
		this.y = y;
		this.f = Direction.valueOf(f);
	}

	/**********
	 * Method
	 **********/
	/**
	 * Rotate robot's facing when input rotate command [LEFT, RIGHT]
	 * LEFT and RIGHT will rotate the robot 90 degrees in the specified direction
	 * @param rotateSite
	 */
	public void rotate(String rotateSite) {
		// rotate site
		if (Direction.EAST.equals(this.f)) {
			switch (RotateSite.valueOf(rotateSite)) {
			case RotateSite.RIGHT:
				this.f = Direction.SOUTH;
				break;
			case RotateSite.LEFT:
				this.f = Direction.NORTH;
				break;
			default:
				break;
			}
		} else if (Direction.WEST.equals(this.f)) {
			switch (RotateSite.valueOf(rotateSite)) {
			case RotateSite.RIGHT:
				this.f = Direction.NORTH;
				break;
			case RotateSite.LEFT:
				this.f = Direction.SOUTH;
				break;
			default:
				break;
			}
		} else if (Direction.NORTH.equals(this.f)) {
			switch (RotateSite.valueOf(rotateSite)) {
			case RotateSite.RIGHT:
				this.f = Direction.EAST;
				break;
			case RotateSite.LEFT:
				this.f = Direction.WEST;
				break;
			default:
				break;
			}
		} else if (Direction.SOUTH.equals(this.f)) {
			switch (RotateSite.valueOf(rotateSite)) {
			case RotateSite.RIGHT:
				this.f = Direction.WEST;
				break;
			case RotateSite.LEFT:
				this.f = Direction.EAST;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * move robot forward position follow robot facing site.
	 * NORTH,EAST will +1 and SOUTH, WEST will -1
	 * robot will not move out of the table 5*5
	 * The origin (0,0) can be considered to be the SOUTH WEST most corner.
	 */
	public void move() {

		// check current position for direction
		// command will be ignored if a robot try move out of table 
		switch (f) {
		case NORTH:
			if (this.y < 5) {
				this.y += 1;
			}
			break;
		case SOUTH:
			if (this.y > 0) {
				this.y -= 1;
			}
			break;
		case EAST:
			if (this.x < 5) {
				this.x += 1;
			}
			break;
		case WEST:
			if (this.x > 0) {
				this.x -= 1;
			}
			break;
		default:
			break;
		}
	}
}

/**************
 * Enums
 **************/
enum Direction {
	NORTH, SOUTH, EAST, WEST;
}

enum RotateSite {
	LEFT, RIGHT;
}
