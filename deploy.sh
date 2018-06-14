#!/bin/bash

export DATABRICKS_TOKEN
export DATABRICKS_HOST

# do not set token if we already mount in 

if [ -f "~/.databrickscfg" ]
then 
  echo "Using supplied configuration"
else
  echo -en "${DATABRICKS_HOST}\n${DATABRICKS_TOKEN}" | databricks configure --token
fi

set -x 

databricks fs ls