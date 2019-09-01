### 1. How to configure binary exercise

  1) from run folder execute runWriter.bat or runReader.bat
  2) The below properties can be configured :
  
     2a) logwriter.properties:
       * writer.buffer.size - configuration for thread local buffer - number of lines thread writes into the file commit.log
        Examples : log.commit.file=D:\\Temp\\commit.log or run as java -cp . -jar logwriter-1.0.0.jar logwriter.properties.
       * a.writer.thread.pool - number of threads type A.
       * b.writer.thread.pool - number of threads type B.
       * writer.total.iterations - total number of lines will be writen into the file. After that, threads will finish the work.
       * log.commit.file -  full path of the file or file has to be in classpath
       
     2b) logreader.properties
       * log.commit.file - full path of the file or file has to be in classpath.
       
### 2. How to run binary excercise

  The excercise can be run by excecuting bat files in run directory.
  
 ### 3. Assumtions ( for simplicity )

  1. There are 2 projects with 2 property files. Every project has a single package.
  2. There is only 1 unit test case writen only for rule 3 - Unique uid for thread cid.
  3. The log file is not rolling if it reaches the certain size.
  4. During App startup there is cleanup happens : the log file will be removed and all previous commits will be lost.
