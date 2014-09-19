#!/bin/bash

find $1 -type f  | xargs grep $2
