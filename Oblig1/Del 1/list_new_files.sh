#!/bin/bash

find $1 -type f -mtime -$2 -print0| xargs -0 du  | sort -n 
