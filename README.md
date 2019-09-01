1. How to run binary exercise
    1) from run folder execute runWriter.bat or runReader.bat
    2) The below properties can be configured
        a) logwriter.properties:
            *** writer.buffer.size - configuration for thread local buffer - number of lines thread writes into the file commit.log
            *** a.writer.thread.pool - number of type A threads
            *** b.writer.thread.pool - number of type B threads
            *** writer.total.iterations - total number of lines will be writen into the file. After that threads will finish the work.
            *** log.commit.file -  full path of file
        b) logreader.properties
            *** log.commit.file - full path of file

2. Assumtions 
