GraalVM native-image configuration for Akka
===========================================
GraalVM allows JVM libraries to include configuration for `native-image` in their JAR artifacts.
See [this blog post](https://medium.com/graalvm/simplifying-native-image-generation-with-maven-plugin-and-embeddable-configuration-d5b283b92f57)
for more details.

Akka does not do that (yet) so this repo publishes artifacts that can be used for building Akka
projects with `native-image` without supplying the whole configuration in the project itself.

graal-akka-actor
----------------
This artifact provides [reflection](https://github.com/oracle/graal/blob/master/substratevm/REFLECTION.md)
configuration for `akka-actor` (based on [previous work by jrudolph](https://github.com/jrudolph/akka-graal))
module and also a substitution to make Akka's default scheduler work with `native-image`.

    "com.github.vmencik" %% "graal-akka-actor" % graalAkkaVersion
    
graal-akka-http
---------------
This artifact provides [reflection](https://github.com/oracle/graal/blob/master/substratevm/REFLECTION.md)
configuration for `akka-http` (including the required parts of `akka-stream`) and also enables support
for http and https [protocols](https://github.com/oracle/graal/blob/master/substratevm/URL-PROTOCOLS.md).

    "com.github.vmencik" %% "graal-akka-http" % graalAkkaVersion

Usage
-----    
Note that the configuration provided by these artifacts is not everything you will need to build
your Akka project with `native-image`.

See the [akka-graal-native](https://github.com/vmencik/akka-graal-native) repository for an example
of Akka HTTP service compiled into native binary. There you can see the rest of the configuration
for `native-image` and also configuration for Akka itself that makes things easier.

Compatibility
-------------
Current version of the artifacts was tested with akka-actor 2.5.25, akka-http 10.1.8, GraalVM 19.0.2
and Scala 2.12.8.

The reflection configuration and the substitution are tightly coupled with Akka internals and will
likely to be updated for future versions.

There are currently no artifacts for Scala 2.13.x because it too will require different configuration
than Scala 2.12.x.
