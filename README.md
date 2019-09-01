### 1. How to run binary exercise <br/>
    1) from run folder execute runWriter.bat or runReader.bat <br/>
    2) The below properties can be configured <br/>
        a) logwriter.properties: <br/>
            *** writer.buffer.size - configuration for thread local buffer - number of lines thread writes into the file commit.log <br/>
            *** a.writer.thread.pool - number of type A threads. <br/>
            *** b.writer.thread.pool - number of type B threads  <br/>
            *** writer.total.iterations - total number of lines will be writen into the file. After that threads will finish the work.<br/> 
            *** log.commit.file -  full path of file <br/>
        b) logreader.properties <br/>
            *** log.commit.file - full path of file <br/>

### 2. Assumtions  <br/>
