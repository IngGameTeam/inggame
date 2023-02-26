const mineflayer = require('mineflayer')


var createBot = function(name) {
    let bot = mineflayer.createBot({
        host: 'localhost',
        username: name,
        port: 25555,
    })
    bot.on('chat', (username, message) => {
      if (username === bot.username) return
      bot.chat(message)
    })
    bot.on('kicked', console.log)
    bot.on('error', console.log)
}


for(let i = 0; i < 50; i++) {
    createBot("test" + i)
}
