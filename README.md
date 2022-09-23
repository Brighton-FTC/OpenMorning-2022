# Open Morning 2022 Demo
## Development Environment Instructions
* Setting Up the Development Environment
  * Clone with GitHub desktop/equivalent
  * Open Android Studio > Open > Select folder
  * Wait for the IDE to sync.
* How to connect to the robot:
  * Connect the laptop to the robot's Control Hub via a USB-C cable (with the smaller end in the robot).
  * In the top bar, click the device selection dropdown (the second dropdown from the left) and select "REV Robotics Control Hub v1.0"
  * Click the debug or play icon to the right of the dropdown, and the code will compile and run in the tabs at the bottom of the screen.
* How to control the robot:
  * Turn on the Driver Station
  * Connect to the Driver Station using [these instructions](https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki/Configuring-Your-Android-Devices#pairing-the-driver-station-to-the-robot-controller). Please note that the passwords have been changed from `password` and saved on the Driver Station itself.
# Useful Resources
## Shortcuts
- [TeamCode Package for our own code](TeamCode/src/main/java/org/firstinspires/ftc/teamcode)
- [Code Examples from FTC](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples)
- [GitHub Project](https://github.com/WebCoder49/FTC-2021/projects/1)
> When copying a sample OpMode, please rememember to remove `@Disabled`
> To add a license key, paste it in <TeamCode/src/main/res/raw/> under `<key-name>.txt`, then you can use `libs.util.PrivateDataReader.get("<key-name">)` to retrieve the key (please see Discord)

## External Links
- [FTC Wiki](https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki)
- Docs
  - [FTC Javadoc Docs](https://javadoc.io/doc/org.firstinspires.ftc)
  - [Android Reference](https://developer.android.com/reference)
