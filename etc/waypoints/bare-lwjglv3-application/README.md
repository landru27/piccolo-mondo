## bare-lwjglv3-application
The tag `bare-lwjglv3-application` refers to a version of Piccolo Mondo that can be used as a bare LWJGLv3 application.  It has a code structure that anticipates a fully fledged application, but has only the barest actual LWJGL / GLFW functionality.  All it does is open a window with a dark red background.  Thus, it goes beyond a typical 'hello world' example, but leaves the 3D display portion entirely open.  It aims to fill a gap for having a starting place that is somewhere between a simplistic 'hello world' example, which needs a whole lot to become a serious application, and a complete open source LWJGL project, which needs a whole lot removed if one wants to do something rather different.

### procure
To work with the `bare-lwjglv3-application` waypoint version of this project, issue the following commands:

```bash
git clone git@github.com:landru27/piccolo-mondo.git
cd piccolo-mondo
git checkout bare-lwjglv3-application
```

### setup
To setup for the application, issue the following commands from the project repository root:

```bash
mkdir dist
mkdir lib

mkdir ~/PiccoloMondo
mkdir ~/PiccoloMondo/logs

cp -ip etc/waypoints/bare-lwjglv3-application/*.json ~/PiccoloMondo
```

### build
To build the application, issue the following command from the project repository root:

```bash
mvn clean deploy -DaltDeploymentRepository=snapshot-repo::default::file:dist/
```

### run
To run the application, issue the following command from the project repository root:

```bash
java -XstartOnFirstThread -classpath target/classes/:lib/* org.voxintus.piccolomondo.launcher.Launcher
```
