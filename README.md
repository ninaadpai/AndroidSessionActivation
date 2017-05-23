# AndroidSessionActivation

This is a session activation method implemented for Android clients posting basic device information to server and receiving ack
acknowledgements in return.

The application can be integrated by using the device utility methods and the asynchronous task which communicates with the server.

The app integrator automatically collects basic data such as Device ID, Location, Device Type, Device OS/Version and Screen Size.

It can optionally collect user's age and gender if the user wishes to enter the information. 

The utility class can collect the following data as well: 
Total Memory,
Available Memory,
Device Name,
MAC Address,
IP Address (v4/v6),
CPU Usage Stats,
If Device size is more than 5 inch,
Device Dimensions
