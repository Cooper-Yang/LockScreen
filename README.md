#LockScreen

For curriculum design purpose

This app will add another layer of Lock Screen, which is design by this app, on top of the original lock screen, as for security reason or something.

Can block Home, Back, Menu Button and the Navigation Bar while lock screen is activated.

Min Sdk version: Android 5.1

Target Sdk Version: Android 5.1

Dependencies:

    Appcompat-v7:22.2.1
    design:22.2.1

##java

###BootReceiver

As to receive the Booting Broadcast

###KeyLockScreenActivity

Self-Design lock screen, using LayoutInflater to inflate which on top of the original one.

Action and Animation of Buttons are implemented mainly by hardcode.

###MainActivity

Through which to set your password

###ScreenService

A service, waiting for signal to launch the KeyLockScreenActivity

##res

###Layout: back.xml

Layout of the self-design lock screen

###Layout: activity_main.xml

Layout of the password-setting interface
