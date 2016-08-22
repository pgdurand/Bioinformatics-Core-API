#Bioinformatics Core API

[![License](https://img.shields.io/badge/license-Affero%20GPL%203.0-blue.svg)](https://www.gnu.org/licenses/agpl-3.0.txt)

##Introduction

This package is a core library of APIs for the [BLAST Filter Tool](https://github.com/pgdurand/BLAST-Filter-Tool):

* to model Rich Search Results objects (e.g. annotated [NCBI BLAST](http://blast.ncbi.nlm.nih.gov/Blast.cgi) results) 
* to provide readers/writers for these objects
* to provide data models for the Bioinformatics Core UI components

##Requirements

Use a [Java Virtual Machine](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.7 (or above) from Oracle. 

*Not tested with any other JVM providers but Oracle... so there is no guarantee that the software will work as expected if not using Oracle's JVM.*

##Library uses

Since this package is a library, its primary purpose targets a use within other softwares. You can see how to use the library by having a look at:

* "test" package in this project: it contains sample sources codes; "TestSerialSystem.java" is of particular interest
* [Blast Filter Tool](https://github.com/pgdurand/BLAST-Filter-Tool) source code: a full example of a running application relying on this library

##License and dependencies

Bioinformatics Core API itself is released under the GNU Affero General Public License, Version 3.0. [AGPL](https://www.gnu.org/licenses/agpl-3.0.txt)

It depends on several thrid-party libraries as stated in the NOTICE.txt file provided with this project.

--
(c) 2006-2016 - Patrick G. Durand
