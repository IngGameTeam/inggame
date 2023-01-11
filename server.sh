GIT_URL=https://Bruce0203:$GH_TOKEN@github.com/IngGameTeam/inggame

#BASEDIR=$(dirname $0)
#cd $BASEDIR
  if [ -d ".git" ]; then
      echo "pull!"
      cd plugins
      git stash
      git stash drop
      git remote set-url origin $GIT_URL
      git pull origin bukkit
      cd ..
      cd ..
  else
      echo "clone!"
      cd ..
      git clone -b bukkit --single-branch $GIT_URL
  fi
  cd inggame
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
#  wget -O server.jar https://ci.codemc.io/job/etil2jz/job/Mirai-1.19/lastSuccessfulBuild/artifact/build/libs/mirai-paperclip-1.19.2-R0.1-SNAPSHOT-reobf.jar
#  VERSION=$(<version.txt)
#  wget -O server.jar https://api.purpurmc.org/v2/purpur/$VERSION/latest/download


#java -Xms12G -Xmx12G -jar server.jar
java -Xms2G -Xmx2G -jar server.jar -DGH_TOKEN="$GH_TOKEN"