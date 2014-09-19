#!/usr/bin/env python

import random   # Random number generator
import os       # Crossplatform OS rutines
import sys      # interpreter tools
from random import randrange

legal_chars = "abcdefghijklmnopqrstuvwxyz"+\
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789_"



def random_string(length=6, prefix="", legal_chars=legal_chars):

    """
Create a random string of text.

Parameters
----------
length : int
    Length of the string (minus the prefix part).
prefix : string
    Prefix the string with some text.
legal_chars : string
    A string of charracter that are allowed to be used in the
    output.

Returns
-------
rnd_str : str
    A string of random charracters.
    """
    # Insert user code here
    rnd_str = prefix.join(random.choice(legal_chars) for _ in range(length))
    return rnd_str



def generate_tree(target, dirs=6, rec_depth=5,files = 5, size = 800,start_time=1388534400,end_time=1406851201000, verbose=False):
    """
Genereate a random folder structure with random names.

Parameters
----------
target : str
    Path to the root where folders are to be created.
dirs : int
    Maximum number of directories to be created per directory.
rec_depth : int
    Maximum directory depth.
files : int
    Maximum number of directories to be created.
size : int
    Maximum size in kilobyte for each file.
start_time : int
    Lower bound for access time (atime) and modified time (mtime)
    allowed in each file.
    Denoted in Unix time format.
end_time : int
    Same as start_time, but for upper bound.
verbose : bool
    Be loud about what to do.
    """
    # Insert user code here
    dir_depth = randrange(2,rec_depth+1)
    dir_Pr_Dir = randrange(2,dirs+1)

    #for x in range (3):

    mkDirs(target,dir_depth,dir_Pr_Dir,files,size,start_time,end_time,verbose)
        
       

def mkDirs(new_dir,dir_deep,dirs,files,size,start_time,end_time,verbose):
    '''    
Parameters
----------
new_dir : str
    Path to the root where folders are to be created.
dirs : int
    Maximum number of directories to be created per directory.
dir_deep : int
    Maximum directory depth.
files : int
    Maximum number of directories to be created.
size : int
    Maximum size in kilobyte for each file.
start_time : int
    Lower bound for access time (atime) and modified time (mtime)
    allowed in each file.
    Denoted in Unix time format.
end_time : int
    Same as start_time, but for upper bound.
verbose : bool
    Be loud about what to do.
    '''

    rand_dirs = randrange(1,dirs)

    if dir_deep>=0 :

        for x in range (1,dirs):  

            new_dir2 = new_dir+"/"+random_string()
            os.makedirs(new_dir2)
            if verbose == True:
                print new_dir2
            populate_tree(new_dir2,random.randint(1,files),size,start_time,end_time,verbose)
            mkDirs(new_dir2,dir_deep-1,dirs,files,size,start_time,end_time,verbose,)
            
        dir_deep-=1
               



def populate_tree(target, files=5, size=800, start_time=1388534400,
        end_time=1406851201000, verbose=False):
    """
Generate random files with random content.

Parameters
----------
target : str
    Path to the file tree where the files are being created.
files : int
    Maximum number of directories to be created.
size : int
    Maximum size in kilobyte for each file.
start_time : int
    Lower bound for access time (atime) and modified time (mtime)
    allowed in each file.
    Denoted in Unix time format.
end_time : int
    Same as start_time, but for upper bound.
verbose : bool
    Be loud about what to do.
    """
    last_modified = randrange(start_time,end_time+1)
    last_accessed = randrange(last_modified,end_time+1)

    for x in range (files):
        filnavn = random_string()
        fill = target + "/"+filnavn
        if os.path.exists(fill):
            print "Fail"
        else:     
            fil = open(fill,"w+")
            if verbose ==True:
                print fill
            rand_size=random.randint(1,3000)
            fil.write(os.urandom(rand_size))

            fil.close()
            os.utime(fill,(last_modified,last_accessed))



# If-test to ensure code only executed if ran as stand-alone app.
if __name__ == "__main__":

    l = len(sys.argv)

    if l < 4:
        print "Not enough arguments included."
        print "usage: %s target dirs files " % sys.argv[0] +\
            "[size rec_depth start end seed verbose]"
        sys.exit(0)

    target = sys.argv[1]
    dirs = int(sys.argv[2])
    files = int(sys.argv[3])

    # And-or trick to use argv only if argv is long enough.
    size = l<5 and 1000 or int(sys.argv[4])
    rec_depth = l<6 and 2 or int(sys.argv[5])
    start = l<7 and 1388534400 or int(sys.argv[6])
    end = l<8 and 1406851200 or int(sys.argv[7])
    seed = [l<9 and "0" or sys.argv[8]][0]
    verbose = [l<10 and "0" or sys.argv[9]][0]

    # Fix the random seed (if not None):

    random.seed(int(seed) or None)

    generate_tree(target, dirs, rec_depth,files,size,start,end, verbose)

