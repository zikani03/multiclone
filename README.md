Multiclone
==========

## What is this, really?

It's another Git utility - I seem to need a lot of these things.

Basically this tool allows you to download multiple repositories
from an organization's public repositories from GitHub.

## Example Usage

If you were interested in getting the following repos: chill, 
diffy, pelikan, secureheaders, cascading, heron and sslconfig from Twitter's 
open-source from GitHub*. You would use the following commands:

```sh
$ java -jar multiclone.jar twitter chill diffy pelikan secureheaders cascading heron sslconfig
```

* Sounds like *Chill, diffy Peli can secure headers cascading her own sslconfig*

## TODO

* Add argparse4j or airbrake's commandline arguments parser
* Prioritize repo downloads by repository size, based on info from GitHub

# LICENSE

                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                        Version 2, December 2004
    
     Copyright (C) 2017 Zikani Nyirenda Mwase
    
     Everyone is permitted to copy and distribute verbatim or modified
     copies of this code, and changing it is allowed as long
     as the name is changed.
    
                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
       TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
    
      0. You just DO WHAT THE FUCK YOU WANT TO.

---

Copyright (c) 2017, zikani03