# MLDS
Source code & demos for presentation "Mining datasets that don't fit in memory with Apache Spark"

## Building

The project is self-contained, it requires only JDK 1.7+ to work. To build the project just run:

    ./sbt build
    
## Opening in IDEA
      
This should be straightforward too: 

* Install IDEA with Scala plugin
* Select `File` -> `New` -> `Project from Existing Sources` in main menu
* Choose `Import from external model` -> `SBT` and follow instructions
        
## Running
        
This project requires Spark 1.4.1. Verify that `spark-shell` and `spark-submit` scripts are available in path and run:
         
    ./dist/bin/mlds.sh <command> [options...]
             
List of available options is printed when running the script without arguments

You can control Spark options with `SPARK_PROFILE` environment variable, for example:

    export SPARK_PROFILE="--driver-memory 4G"
    
This also might be used to run the demo on YARN cluster, control the number of executors, etc.    
             
## Available demos
             
### Simple demos

Word count

    ./dist/bin/mlds.sh word-count
             
Shuffle
             
    ./dist/bin/mlds.sh shuffle
             
### Shuffle performance demos

Default serialization

     ./dist/bin/mlds.sh default-serializer 
             
Registered serialization
             
     ./dist/bin/mlds.sh registered-serializer 
             
Custom serialization with primitive arrays
             
     ./dist/bin/mlds.sh custom-serializer 
             
Shuffle performance demos support optional `scale` argument that allow to control data size. 
Each partition is around 100Mb. Default value is 4.  
             
### Pre-partition demos

Generate prepartitioned dataset

     ./dist/bin/mlds.sh gen-prepartitioned -p <path> 
             
Process prepartitioned dataset with a shuffle (ignoring partitioning knowledge)
             
     ./dist/bin/mlds.sh shuffle-prepartitioned -p <path> 
             
Process prepartitioned dataset with a shuffle (ignoring partitioning knowledge)
             
     ./dist/bin/mlds.sh read-prepartitioned -p <path> 
             
             