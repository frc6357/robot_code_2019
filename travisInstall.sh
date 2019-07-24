#!/bin/bash

WPILIB_VERSION="2019.4.1"

cwd=$(pwd)

wget -o https://github.com/wpilibsuite/allwpilib/releases/download/v2019.4.1/WPILib_Linux-$WPILIB_VERSION.tar.gz

tar -xvzf WPILib_Linux-$WPILIB_VERSION.tar.gz

mkdir frc2019

mv -v WPILib_Linux-$WPILIB_VERSION.tar.gz/* ./frc2019

cd ./frc2019/tools

python3 ToolsUpdator.py

cd $cwd

mkdir ./frc2019/SpringKonstant

cp -r ./frc6357/* ./frc2019/SpringKonstant/

cd $cwd


