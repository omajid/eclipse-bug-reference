Synopsis
========

**eclipse-bug-reference** is an eclipse plugin to automatically make
bug references (such as `BUG 123`) in code or documentation into
clickable hyperlinks that will directly take you to the bug report.

![Screenshot](screenshots/main.png?raw=true "Screenshot")


Building
========

This is a tycho-based project. Use maven to build:

    $ mvn clean verify


Installation
============

After building, a p2 repository should be available at:

    com.github.omajid.bugreference.p2-repo/target/repository

To install this plugin, use `Help` -> `Install New Software`. Add the location
above as a new local repository: click `Add` in the dialog that pops up then
select `Local`. After giving the repo a name and clicking `OK` you should be
able to install the `Bug Reference` plugin.


Usage
=====

Once the plugin is installed, you can simply `Ctrl-click` on any text
that looks like a bug link (such as `JDK-123`) to go directly to the
bug.

You can customize, add and remove additional expressions to be identified as a
link via `Window` -> `Preferences` -> `Bug Reference`.


Filing Bugs and Contributing
============================

Please report bugs via Github. Pull Requests are even better!

