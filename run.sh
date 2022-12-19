BASEDIR=$(dirname $0)
cd $BASEDIR
while :
do
chmod +x ./update.sh
./update.sh
mkdir customized_minigame
cp -r customized_minigame-config.yml customized_minigame/paper-world.yml
java -Xms12G -Xmx12G -jar server.jar
done
