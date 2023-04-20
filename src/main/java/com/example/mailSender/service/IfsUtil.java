package com.example.mailSender.service;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.IFSFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class IfsUtil {


    private static final String CLASSNAME = "IfsUtil";
    private final Map<String, List<IFSFile>> allDuplicateIFSFiles = new HashMap<>();

    public static AS400 getIfsAs400Connection() {


  /*      String ifsSystem = AppUtils.getProperty("host.store");

        String usr = AppUtils.getProperty("host.dft.user");

        String pass = AppUtils.getProperty("host.dft.pass");

   */


//        return new AS400( "${ifs.host}","${spring.datasource.username}",
//        "${spring.datasource.password}");


        return new AS400("129.40.95.105", "ABHISHEK", "welcome");
    }


    public IFSFile getIfsFile(String dir) throws IOException {
        return new IFSFile(getIfsAs400Connection(), dir);

    }

    public IFSFile findAllFilesFormIFSDirAndPutIntoMap(IFSFile file, Map<String, IFSFile> allIfsFiles)

            throws IOException {

        if (file.isDirectory()) {

            IFSFile[] arr = file.listFiles();

            for (IFSFile f : arr) {

                IFSFile found = findAllFilesFormIFSDirAndPutIntoMap(f, allIfsFiles);

                if (found != null)

                    return found;

            }

        } else {

            IFSFile duplicate = allIfsFiles.put(file.getName(), file);

            if (duplicate != null) {

                if (allDuplicateIFSFiles.containsKey(duplicate.getName())) {

                    allDuplicateIFSFiles.get(duplicate.getName()).add(file);

                } else {

                    allDuplicateIFSFiles.put(duplicate.getName(), new ArrayList<>(Arrays.asList(duplicate, file)));

                }

            }

        }

        return null;


    }


}
