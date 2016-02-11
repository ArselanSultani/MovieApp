#!/bin/bash

find $1 -type f -size +$2k -exec rm -rf {} \;
