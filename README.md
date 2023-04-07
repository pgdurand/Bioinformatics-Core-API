# Bioinformatics Core API

![build](https://github.com/pgdurand/Bioinformatics-Core-API/actions/workflows/ant.yml/badge.svg) [![License AGPL](https://img.shields.io/badge/license-Affero%20GPL%203.0-blue.svg)](https://www.gnu.org/licenses/agpl-3.0.txt) [![](https://tokei.rs/b1/github/pgdurand/Bioinformatics-Core-API?category=code)](https://github.com/pgdurand/Bioinformatics-Core-API) [![](https://img.shields.io/badge/platform-Java--1.8+-yellow.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) [![](https://img.shields.io/badge/run_on-Linux--Mac_OSX--Windows-yellowgreen.svg)]()

## Introduction

This package is a core library of APIs:

* to model Rich Search Results objects (e.g. annotated [NCBI BLAST](http://blast.ncbi.nlm.nih.gov/Blast.cgi) results) 
* to model DNA and protein sequences
* to model features / annotations
* to provide readers and writers for these objects

In turn, these object-oriented data models form the basic foundation of the [Bioinformatics Core UI components](https://github.com/pgdurand/Bioinformatics-UI-API)

All in all, [Core](https://github.com/pgdurand/Bioinformatics-Core-API) and [Core-UI](https://github.com/pgdurand/Bioinformatics-UI-API) APIs are the building blocks to make bioinformatics tools;  *e.g.* [BLAST Filter](https://github.com/pgdurand/BLAST-Filter-Tool), [BioDocumentViewer](https://github.com/pgdurand/BioDocumentViewer) and [BlastViewer](https://github.com/pgdurand/BlastViewer)

## Requirements

Use a [Java Virtual Machine](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 (or above) from Oracle. 

*Not tested with any other JVM providers but Oracle... so there is no guarantee that the software will work as expected if not using Oracle's JVM.*

## Library uses

Since this package is a library, its primary purpose targets a use within other softwares. You can see how to use the library by having a look at:

* "test" package in this project: it contains sample sources codes; "TestSerialSystem.java" and "TestSFeatureSystem.java" are of particular interest
* [Blast Filter Tool](https://github.com/pgdurand/BLAST-Filter-Tool) source code: a full example of a running application relying on this library
* [Bioinformatics Core UI components](https://github.com/pgdurand/Bioinformatics-UI-API): a set of viewers of sequence/feature/etc objects

Added in release 6.2.0 (Sept. 2020), there is a tool available:

* ImportIprScanPredictions: import Iprscan predicted domains and adds that information into a BLAST or PLAST result file.

See also [Wiki](https://github.com/pgdurand/Bioinformatics-Core-API/wiki).

## License and dependencies

Bioinformatics Core API itself is released under the GNU Affero General Public License, Version 3.0. [AGPL](https://www.gnu.org/licenses/agpl-3.0.txt)

It depends on several thrid-party libraries as stated in the NOTICE.txt file provided with this project.

--
(c) 2003-2020 - Patrick G. Durand
