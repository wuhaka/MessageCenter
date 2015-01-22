# MessageCenter
This is used for communication for any two files, such as Activity, Fragment or other Android components even Java File,
and only need share heap memory among these files. In other words, they should be in the same process. The tool can replace
listener or observer comfortably.

if communication between two files, you can notify 'class' via
```JAVA
MessageCenter.notify(Afragment.class,p1,p2..)
```
surely,you should register in Afragment via 
```JAVA
MessageCenter.register(this)
```
and a default method 
```JAVA 
onHandMessage()
```
is the callback with any number of parameters.


if a one-to-many communication, you can notify 'method' via 
```JAVA
MessageCenter.notify(MessageMethod.AMETHOD, p1,p2..)
````
surely,you should register in the designated components via
```JAVA
MessageCenter.register(MessageMethod.AMETHOD)
```
and you should wirite 
```JAVA
public void amethod(p1, p2...)
```
You can add an annotation '@Invoked' identitying called by "xxx.invoke(p1,p2..)".
