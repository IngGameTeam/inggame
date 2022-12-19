cd ..
FILE=inggame/.git
if [ -f "$FILE" ]; then
    cd inggame
    git pull
    cd ..
else
    git clone -b bukkit --single-branch https://github.com/IngGameTeam/inggame
fi
cd inggame
#VERSION=$(<version.txt)
mkdir plugins
cd plugins
#sudo apt install -y jq
function plugin() {
    latest=$(curl -sL https://api.github.com/repos/$1/releases/latest | jq -r ".tag_name")
    cd plugins
    wget -O $2.jar https://github.com/$1/releases/download/$latest/$2-$latest.jar
    cd ..
}

plugin IntellectualSites/FastAsyncWorldEdit FastAsyncWorldEdit-Bukkit
plugin ViaVersion/ViaVersion ViaVersion
plugin ViaVersion/ViaBackwards ViaBackwards
# plugin EssentialsX/Essentials EssentialsX
#wget -O minecraft_server.$VERSION.jar https://api.purpurmc.org/v2/purpur/$VERSION/latest/download
wget -O server.jar https://ci.codemc.io/job/etil2jz/job/Mirai-1.19/lastSuccessfulBuild/artifact/build/libs/mirai-paperclip-1.19.2-R0.1-SNAPSHOT-reobf.jar




#BUILD_NUMBER=$(curl 'https://api.papermc.io/v2/projects/paper/versions/1.19.2/' | jq '.builds'[-1])
#wget -O minecraft_server.$VERSION.jar https://api.papermc.io/v2/projects/paper/versions/$VERSION/builds/$BUILD_NUMBER/downloads/paper-$VERSION-$BUILD_NUMBER.jar
