Multiclone
==========

## What is this, really?

It's another Git utility - I seem to need a lot of these things.

Basically this tool allows you to download multiple repositories
from an organization's public repositories from GitHub.

## Example Usage

If you were interested in getting the following repos: [chill](https://github.com/twitter/chill), 
[diffy](https://github.com/twitter/diffy), [pelikan](https://github.com/twitter/pelikan), [secureheaders](https://github.com/twitter/secureheaders), [cascading](https://github.com/twitter/cascading), [heron](https://github.com/twitter/heron) and [sslconfig](https://github.com/twitter/sslconfig) from [Twitter's open-source from GitHub](https://github.com/twitter)*. You would use the following commands:

```sh
$ java -jar multiclone.jar twitter chill diffy pelikan secureheaders cascading heron sslconfig
```

*Sounds like *Chill diffy, Peli can secure headers cascading her own sslconfig* - don't ask me what that means.

## TODO

* **WRITE IT IN RUST!!!**

* Prioritize repo downloads by repository size, based on info from GitHub/Bitbucket (iff possible)
* Allow user to specify custom directory for repos to go into
* Clone submodules

# Usage 

```
$ multiclone [ORGANISATION] [REPOSITORY NAMES...]
```

You will have to build it first, yo.

```sh
$ git clone https://github.com/zikani03/multiclone.git
$ cd multiclone
$ mvn clean install
$ chmod +x ./multiclone.sh
$ # Download some repositories from https://github.com/creditdatamw
$ multiclone creditdatamw zerocell spark-pentaho-report cakephp-audit-log
$ ls creditdatamw
zerocell  spark-pentaho-report  cakephp-audit-log
```

# LICENSE

                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                        Version 2, December 2004
    
     Copyright (C) 2018 Zikani Nyirenda Mwase
    
     Everyone is permitted to copy and distribute verbatim or modified
     copies of this code, and changing it is allowed as long
     as the name is changed.
    
                DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
       TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
    
      0. You just DO WHAT THE FUCK YOU WANT TO.

---

Copyright (c) 2018, Zikani Nyirenda Mwase