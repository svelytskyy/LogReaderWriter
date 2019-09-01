### 1. How to run binary exercise

  1) from run folder execute runWriter.bat or runReader.bat
  2) The below properties can be configured
     a) logwriter.properties:
       a1) writer.buffer.size - configuration for thread local buffer - number of lines thread writes into the file commit.log
       b1) a.writer.thread.pool - number of type A threads.
       c1) b.writer.thread.pool - number of type B threads
       d1) writer.total.iterations - total number of lines will be writen into the file. After that threads will finish the work.
       e1) log.commit.file -  full path of file
     b) logreader.properties
       a1) log.commit.file - full path of file

### 2. Assumtions
