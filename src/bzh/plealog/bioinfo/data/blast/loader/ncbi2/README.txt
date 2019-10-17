XSD file from:
https://www.ncbi.nlm.nih.gov/data_specs/schema/

File retrived on Oct 2019: NCBI_BlastOutput2.mod.xsd
Renamed to: NCBI_BlastOutput2.xsd

Before JAXB class generation using:
cd <core-api-home>
xjc -d src -p src.bzh.plealog.bioinfo.data.blast.loader.ncbi2 src/bzh/plealog/bioinfo/data/blast/loader/ncbi2/NCBI_BlastOutput2.xsd
