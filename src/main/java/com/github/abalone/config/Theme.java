package com.github.abalone.config;

import com.github.abalone.view.Window;
import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sardemff7
 */
public class Theme extends ConstraintValue<String>
{
    static HashSet<String> list = null;

    @Override
    protected void initConstraint()
    {
        if ( Theme.list == null )
        {
            Theme.list = new HashSet<String>();
            File themesDir = new File(Window.class.getResource("game").getPath());
            if ( themesDir.isDirectory() )
            {
                File[] listFiles = themesDir.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                });

                for ( Integer i = 0 ; i < listFiles.length ; ++ i )
                {
                    File f = listFiles[i];
                    Theme.list.add(f.getName());
                }
            }
        }
    }

    @Override
    protected Boolean check(String value) {
        return Theme.list.contains((String) value);
    }

    @Override
    public Set<String> getList() {
        return Collections.unmodifiableSet(Theme.list);
    }

    public Theme(String description, String value)
    {
        super(description, value);
    }
}
