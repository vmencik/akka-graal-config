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
module and also substitutions to make Akka's default scheduler and Scala 2.13's
`Statics.releaseFence` work with `native-image`.

    "com.github.vmencik" %% "graal-akka-actor" % graalAkkaVersion
    
graal-akka-stream
----------------
This artifact provides [reflection](https://github.com/oracle/graal/blob/master/substratevm/REFLECTION.md)
configuration for `akka-stream` module.

    "com.github.vmencik" %% "graal-akka-stream" % graalAkkaVersion

graal-akka-http
---------------
This artifact provides [reflection](https://github.com/oracle/graal/blob/master/substratevm/REFLECTION.md)
configuration for `akka-http` and also enables support for http and https [protocols](https://github.com/oracle/graal/blob/master/substratevm/URL-PROTOCOLS.md).

    "com.github.vmencik" %% "graal-akka-http" % graalAkkaVersion

graal-akka-stream
----------------
This artifact provides [reflection](https://github.com/oracle/graal/blob/master/substratevm/REFLECTION.md)
configuration for `akka-slf4j` module.

    "com.github.vmencik" %% "graal-akka-slf4j" % graalAkkaVersion

graal-akka-remote
-----------------

To use Akka Remote with Graal:

* configure the Artery TCP implementation
* exclude Netty and Aeron from your dependency tree
* pass the `--allow-incomplete-classpath` parameter to `native-image`

Usage
-----    
Note that the configuration provided by these artifacts is not everything you will need to build
your Akka project with `native-image`.

See the [akka-graal-native](https://github.com/vmencik/akka-graal-native) repository for an example
of Akka HTTP service compiled into native binary. There you can see the rest of the configuration
for `native-image` and also configuration for Akka itself that makes things easier.

Compatibility
-------------
Current version of the artifacts was tested with:

  * akka-actor 2.5.23
  * akka-http 10.1.8
  * GraalVM 19.0.2
  * Scala 2.12.8 and 2.13.0

The reflection configuration and the substitutions are tightly coupled with Akka internals and will
likely need to be updated for future versions.

The artifacts are cross-published for Scala 2.13 and Scala 2.12.
