package io.github.inggameteam.inggame.player.bukkit

import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.*
import org.bukkit.block.data.BlockData
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.entity.*
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.*
import org.bukkit.map.MapView
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.profile.PlayerProfile
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.BoundingBox
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import java.net.InetSocketAddress
import java.util.*

class NotImplementedPlayer : Player {
    override fun getAttribute(attribute: Attribute): AttributeInstance? {
        throw AssertionError("player is offline")
    }

    override fun setMetadata(metadataKey: String, newMetadataValue: MetadataValue) {
        throw AssertionError("player is offline")
    }

    override fun getMetadata(metadataKey: String): MutableList<MetadataValue> {
        throw AssertionError("player is offline")
    }

    override fun hasMetadata(metadataKey: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun removeMetadata(metadataKey: String, owningPlugin: Plugin) {
        throw AssertionError("player is offline")
    }

    override fun isOp(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setOp(value: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isPermissionSet(name: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        throw AssertionError("player is offline")
    }

    override fun hasPermission(name: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun hasPermission(perm: Permission): Boolean {
        throw AssertionError("player is offline")
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment {
        throw AssertionError("player is offline")
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment {
        throw AssertionError("player is offline")
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment? {
        throw AssertionError("player is offline")
    }

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment? {
        throw AssertionError("player is offline")
    }

    override fun removeAttachment(attachment: PermissionAttachment) {
        throw AssertionError("player is offline")
    }

    override fun recalculatePermissions() {
        throw AssertionError("player is offline")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        throw AssertionError("player is offline")
    }

    override fun sendMessage(message: String) {
        throw AssertionError("player is offline")
    }

    override fun sendMessage(vararg messages: String?) {
        throw AssertionError("player is offline")
    }

    override fun sendMessage(sender: UUID?, message: String) {
        throw AssertionError("player is offline")
    }

    override fun sendMessage(sender: UUID?, vararg messages: String?) {
        throw AssertionError("player is offline")
    }

    override fun getServer(): Server {
        throw AssertionError("player is offline")
    }

    override fun getName(): String {
        throw AssertionError("player is offline")
    }

    override fun spigot(): Player.Spigot {
        throw AssertionError("player is offline")
    }

    override fun getCustomName(): String? {
        throw AssertionError("player is offline")
    }

    override fun setCustomName(name: String?) {
        throw AssertionError("player is offline")
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        throw AssertionError("player is offline")
    }

    override fun getLocation(): Location {
        throw AssertionError("player is offline")
    }

    override fun getLocation(loc: Location?): Location? {
        throw AssertionError("player is offline")
    }

    override fun setVelocity(velocity: Vector) {
        throw AssertionError("player is offline")
    }

    override fun getVelocity(): Vector {
        throw AssertionError("player is offline")
    }

    override fun getHeight(): Double {
        throw AssertionError("player is offline")
    }

    override fun getWidth(): Double {
        throw AssertionError("player is offline")
    }

    override fun getBoundingBox(): BoundingBox {
        throw AssertionError("player is offline")
    }

    override fun isOnGround(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isInWater(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getWorld(): World {
        throw AssertionError("player is offline")
    }

    override fun setRotation(yaw: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun teleport(location: Location): Boolean {
        throw AssertionError("player is offline")
    }

    override fun teleport(location: Location, cause: PlayerTeleportEvent.TeleportCause): Boolean {
        throw AssertionError("player is offline")
    }

    override fun teleport(destination: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun teleport(destination: Entity, cause: PlayerTeleportEvent.TeleportCause): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getNearbyEntities(x: Double, y: Double, z: Double): MutableList<Entity> {
        throw AssertionError("player is offline")
    }

    override fun getEntityId(): Int {
        throw AssertionError("player is offline")
    }

    override fun getFireTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun getMaxFireTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun setFireTicks(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun setVisualFire(fire: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isVisualFire(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getFreezeTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun getMaxFreezeTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun setFreezeTicks(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun isFrozen(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun remove() {
        throw AssertionError("player is offline")
    }

    override fun isDead(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isValid(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isPersistent(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setPersistent(persistent: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getPassenger(): Entity? {
        throw AssertionError("player is offline")
    }

    override fun setPassenger(passenger: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getPassengers(): MutableList<Entity> {
        throw AssertionError("player is offline")
    }

    override fun addPassenger(passenger: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun removePassenger(passenger: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isEmpty(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun eject(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getFallDistance(): Float {
        throw AssertionError("player is offline")
    }

    override fun setFallDistance(distance: Float) {
        throw AssertionError("player is offline")
    }

    override fun setLastDamageCause(event: EntityDamageEvent?) {
        throw AssertionError("player is offline")
    }

    override fun getLastDamageCause(): EntityDamageEvent? {
        throw AssertionError("player is offline")
    }

    override fun getUniqueId(): UUID {
        throw AssertionError("player is offline")
    }

    override fun getTicksLived(): Int {
        throw AssertionError("player is offline")
    }

    override fun setTicksLived(value: Int) {
        throw AssertionError("player is offline")
    }

    override fun playEffect(loc: Location, effect: Effect, data: Int) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> playEffect(loc: Location, effect: Effect, data: T?) {
        throw AssertionError("player is offline")
    }

    override fun playEffect(type: EntityEffect) {
        throw AssertionError("player is offline")
    }

    override fun getType(): EntityType {
        throw AssertionError("player is offline")
    }

    override fun getSwimSound(): Sound {
        throw AssertionError("player is offline")
    }

    override fun getSwimSplashSound(): Sound {
        throw AssertionError("player is offline")
    }

    override fun getSwimHighSpeedSplashSound(): Sound {
        throw AssertionError("player is offline")
    }

    override fun isInsideVehicle(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun leaveVehicle(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getVehicle(): Entity? {
        throw AssertionError("player is offline")
    }

    override fun setCustomNameVisible(flag: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isCustomNameVisible(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setGlowing(flag: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isGlowing(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setInvulnerable(flag: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isInvulnerable(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isSilent(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setSilent(flag: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun hasGravity(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setGravity(gravity: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getPortalCooldown(): Int {
        throw AssertionError("player is offline")
    }

    override fun setPortalCooldown(cooldown: Int) {
        throw AssertionError("player is offline")
    }

    override fun getScoreboardTags(): MutableSet<String> {
        throw AssertionError("player is offline")
    }

    override fun addScoreboardTag(tag: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun removeScoreboardTag(tag: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getPistonMoveReaction(): PistonMoveReaction {
        throw AssertionError("player is offline")
    }

    override fun getFacing(): BlockFace {
        throw AssertionError("player is offline")
    }

    override fun getPose(): Pose {
        throw AssertionError("player is offline")
    }

    override fun getSpawnCategory(): SpawnCategory {
        throw AssertionError("player is offline")
    }

    override fun damage(amount: Double) {
        throw AssertionError("player is offline")
    }

    override fun damage(amount: Double, source: Entity?) {
        throw AssertionError("player is offline")
    }

    override fun getHealth(): Double {
        throw AssertionError("player is offline")
    }

    override fun setHealth(health: Double) {
        throw AssertionError("player is offline")
    }

    override fun getAbsorptionAmount(): Double {
        throw AssertionError("player is offline")
    }

    override fun setAbsorptionAmount(amount: Double) {
        throw AssertionError("player is offline")
    }

    override fun getMaxHealth(): Double {
        throw AssertionError("player is offline")
    }

    override fun setMaxHealth(health: Double) {
        throw AssertionError("player is offline")
    }

    override fun resetMaxHealth() {
        throw AssertionError("player is offline")
    }

    override fun <T : Projectile?> launchProjectile(projectile: Class<out T>): T & Any {
        throw AssertionError("player is offline")
    }

    override fun <T : Projectile?> launchProjectile(projectile: Class<out T>, velocity: Vector?): T & Any {
        throw AssertionError("player is offline")
    }

    override fun getEyeHeight(): Double {
        throw AssertionError("player is offline")
    }

    override fun getEyeHeight(ignorePose: Boolean): Double {
        throw AssertionError("player is offline")
    }

    override fun getEyeLocation(): Location {
        throw AssertionError("player is offline")
    }

    override fun getLineOfSight(transparent: MutableSet<Material>?, maxDistance: Int): MutableList<Block> {
        throw AssertionError("player is offline")
    }

    override fun getTargetBlock(transparent: MutableSet<Material>?, maxDistance: Int): Block {
        throw AssertionError("player is offline")
    }

    override fun getLastTwoTargetBlocks(transparent: MutableSet<Material>?, maxDistance: Int): MutableList<Block> {
        throw AssertionError("player is offline")
    }

    override fun getTargetBlockExact(maxDistance: Int): Block? {
        throw AssertionError("player is offline")
    }

    override fun getTargetBlockExact(maxDistance: Int, fluidCollisionMode: FluidCollisionMode): Block? {
        throw AssertionError("player is offline")
    }

    override fun rayTraceBlocks(maxDistance: Double): RayTraceResult? {
        throw AssertionError("player is offline")
    }

    override fun rayTraceBlocks(maxDistance: Double, fluidCollisionMode: FluidCollisionMode): RayTraceResult? {
        throw AssertionError("player is offline")
    }

    override fun getRemainingAir(): Int {
        throw AssertionError("player is offline")
    }

    override fun setRemainingAir(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getMaximumAir(): Int {
        throw AssertionError("player is offline")
    }

    override fun setMaximumAir(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getArrowCooldown(): Int {
        throw AssertionError("player is offline")
    }

    override fun setArrowCooldown(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getArrowsInBody(): Int {
        throw AssertionError("player is offline")
    }

    override fun setArrowsInBody(count: Int) {
        throw AssertionError("player is offline")
    }

    override fun getMaximumNoDamageTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun setMaximumNoDamageTicks(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getLastDamage(): Double {
        throw AssertionError("player is offline")
    }

    override fun setLastDamage(damage: Double) {
        throw AssertionError("player is offline")
    }

    override fun getNoDamageTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun setNoDamageTicks(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getKiller(): Player? {
        throw AssertionError("player is offline")
    }

    override fun addPotionEffect(effect: PotionEffect): Boolean {
        throw AssertionError("player is offline")
    }

    override fun addPotionEffect(effect: PotionEffect, force: Boolean): Boolean {
        throw AssertionError("player is offline")
    }

    override fun addPotionEffects(effects: MutableCollection<PotionEffect>): Boolean {
        throw AssertionError("player is offline")
    }

    override fun hasPotionEffect(type: PotionEffectType): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getPotionEffect(type: PotionEffectType): PotionEffect? {
        throw AssertionError("player is offline")
    }

    override fun removePotionEffect(type: PotionEffectType) {
        throw AssertionError("player is offline")
    }

    override fun getActivePotionEffects(): MutableCollection<PotionEffect> {
        throw AssertionError("player is offline")
    }

    override fun hasLineOfSight(other: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getRemoveWhenFarAway(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setRemoveWhenFarAway(remove: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getEquipment(): EntityEquipment? {
        throw AssertionError("player is offline")
    }

    override fun setCanPickupItems(pickup: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getCanPickupItems(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isLeashed(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getLeashHolder(): Entity {
        throw AssertionError("player is offline")
    }

    override fun setLeashHolder(holder: Entity?): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isGliding(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setGliding(gliding: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isSwimming(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setSwimming(swimming: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isRiptiding(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isSleeping(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isClimbing(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setAI(ai: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun hasAI(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun attack(target: Entity) {
        throw AssertionError("player is offline")
    }

    override fun swingMainHand() {
        throw AssertionError("player is offline")
    }

    override fun swingOffHand() {
        throw AssertionError("player is offline")
    }

    override fun setCollidable(collidable: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isCollidable(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getCollidableExemptions(): MutableSet<UUID> {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> getMemory(memoryKey: MemoryKey<T>): T? {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> setMemory(memoryKey: MemoryKey<T>, memoryValue: T?) {
        throw AssertionError("player is offline")
    }

    override fun getHurtSound(): Sound? {
        throw AssertionError("player is offline")
    }

    override fun getDeathSound(): Sound? {
        throw AssertionError("player is offline")
    }

    override fun getFallDamageSound(fallHeight: Int): Sound {
        throw AssertionError("player is offline")
    }

    override fun getFallDamageSoundSmall(): Sound {
        throw AssertionError("player is offline")
    }

    override fun getFallDamageSoundBig(): Sound {
        throw AssertionError("player is offline")
    }

    override fun getDrinkingSound(itemStack: ItemStack): Sound {
        throw AssertionError("player is offline")
    }

    override fun getEatingSound(itemStack: ItemStack): Sound {
        throw AssertionError("player is offline")
    }

    override fun canBreatheUnderwater(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getCategory(): EntityCategory {
        throw AssertionError("player is offline")
    }

    override fun setInvisible(invisible: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isInvisible(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getInventory(): PlayerInventory {
        throw AssertionError("player is offline")
    }

    override fun getEnderChest(): Inventory {
        throw AssertionError("player is offline")
    }

    override fun getMainHand(): MainHand {
        throw AssertionError("player is offline")
    }

    override fun setWindowProperty(prop: InventoryView.Property, value: Int): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getEnchantmentSeed(): Int {
        throw AssertionError("player is offline")
    }

    override fun setEnchantmentSeed(seed: Int) {
        throw AssertionError("player is offline")
    }

    override fun getOpenInventory(): InventoryView {
        throw AssertionError("player is offline")
    }

    override fun openInventory(inventory: Inventory): InventoryView? {
        throw AssertionError("player is offline")
    }

    override fun openInventory(inventory: InventoryView) {
        throw AssertionError("player is offline")
    }

    override fun openWorkbench(location: Location?, force: Boolean): InventoryView? {
        throw AssertionError("player is offline")
    }

    override fun openEnchanting(location: Location?, force: Boolean): InventoryView? {
        throw AssertionError("player is offline")
    }

    override fun openMerchant(trader: Villager, force: Boolean): InventoryView? {
        throw AssertionError("player is offline")
    }

    override fun openMerchant(merchant: Merchant, force: Boolean): InventoryView? {
        throw AssertionError("player is offline")
    }

    override fun closeInventory() {
        throw AssertionError("player is offline")
    }

    override fun getItemInHand(): ItemStack {
        throw AssertionError("player is offline")
    }

    override fun setItemInHand(item: ItemStack?) {
        throw AssertionError("player is offline")
    }

    override fun getItemOnCursor(): ItemStack {
        throw AssertionError("player is offline")
    }

    override fun setItemOnCursor(item: ItemStack?) {
        throw AssertionError("player is offline")
    }

    override fun hasCooldown(material: Material): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getCooldown(material: Material): Int {
        throw AssertionError("player is offline")
    }

    override fun setCooldown(material: Material, ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getSleepTicks(): Int {
        throw AssertionError("player is offline")
    }

    override fun sleep(location: Location, force: Boolean): Boolean {
        throw AssertionError("player is offline")
    }

    override fun wakeup(setSpawnLocation: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getBedLocation(): Location {
        throw AssertionError("player is offline")
    }

    override fun getGameMode(): GameMode {
        throw AssertionError("player is offline")
    }

    override fun setGameMode(mode: GameMode) {
        throw AssertionError("player is offline")
    }

    override fun isBlocking(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isHandRaised(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getItemInUse(): ItemStack? {
        throw AssertionError("player is offline")
    }

    override fun getExpToLevel(): Int {
        throw AssertionError("player is offline")
    }

    override fun getAttackCooldown(): Float {
        throw AssertionError("player is offline")
    }

    override fun discoverRecipe(recipe: NamespacedKey): Boolean {
        throw AssertionError("player is offline")
    }

    override fun discoverRecipes(recipes: MutableCollection<NamespacedKey>): Int {
        throw AssertionError("player is offline")
    }

    override fun undiscoverRecipe(recipe: NamespacedKey): Boolean {
        throw AssertionError("player is offline")
    }

    override fun undiscoverRecipes(recipes: MutableCollection<NamespacedKey>): Int {
        throw AssertionError("player is offline")
    }

    override fun hasDiscoveredRecipe(recipe: NamespacedKey): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getDiscoveredRecipes(): MutableSet<NamespacedKey> {
        throw AssertionError("player is offline")
    }

    override fun getShoulderEntityLeft(): Entity? {
        throw AssertionError("player is offline")
    }

    override fun setShoulderEntityLeft(entity: Entity?) {
        throw AssertionError("player is offline")
    }

    override fun getShoulderEntityRight(): Entity? {
        throw AssertionError("player is offline")
    }

    override fun setShoulderEntityRight(entity: Entity?) {
        throw AssertionError("player is offline")
    }

    override fun dropItem(dropAll: Boolean): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getExhaustion(): Float {
        throw AssertionError("player is offline")
    }

    override fun setExhaustion(value: Float) {
        throw AssertionError("player is offline")
    }

    override fun getSaturation(): Float {
        throw AssertionError("player is offline")
    }

    override fun setSaturation(value: Float) {
        throw AssertionError("player is offline")
    }

    override fun getFoodLevel(): Int {
        throw AssertionError("player is offline")
    }

    override fun setFoodLevel(value: Int) {
        throw AssertionError("player is offline")
    }

    override fun getSaturatedRegenRate(): Int {
        throw AssertionError("player is offline")
    }

    override fun setSaturatedRegenRate(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getUnsaturatedRegenRate(): Int {
        throw AssertionError("player is offline")
    }

    override fun setUnsaturatedRegenRate(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getStarvationRate(): Int {
        throw AssertionError("player is offline")
    }

    override fun setStarvationRate(ticks: Int) {
        throw AssertionError("player is offline")
    }

    override fun getLastDeathLocation(): Location? {
        throw AssertionError("player is offline")
    }

    override fun setLastDeathLocation(location: Location?) {
        throw AssertionError("player is offline")
    }

    override fun fireworkBoost(fireworkItemStack: ItemStack): Firework? {
        throw AssertionError("player is offline")
    }

    override fun isConversing(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun acceptConversationInput(input: String) {
        throw AssertionError("player is offline")
    }

    override fun beginConversation(conversation: Conversation): Boolean {
        throw AssertionError("player is offline")
    }

    override fun abandonConversation(conversation: Conversation) {
        throw AssertionError("player is offline")
    }

    override fun abandonConversation(conversation: Conversation, details: ConversationAbandonedEvent) {
        throw AssertionError("player is offline")
    }

    override fun sendRawMessage(message: String) {
        throw AssertionError("player is offline")
    }

    override fun sendRawMessage(sender: UUID?, message: String) {
        throw AssertionError("player is offline")
    }

    override fun serialize(): MutableMap<String, Any> {
        throw AssertionError("player is offline")
    }

    override fun isOnline(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getPlayerProfile(): PlayerProfile {
        throw AssertionError("player is offline")
    }

    override fun isBanned(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isWhitelisted(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setWhitelisted(value: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getPlayer(): Player? {
        throw AssertionError("player is offline")
    }

    override fun getFirstPlayed(): Long {
        throw AssertionError("player is offline")
    }

    override fun getLastPlayed(): Long {
        throw AssertionError("player is offline")
    }

    override fun hasPlayedBefore(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun getBedSpawnLocation(): Location? {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic) {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic, material: Material) {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic, material: Material, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic, entityType: EntityType) {
        throw AssertionError("player is offline")
    }

    override fun incrementStatistic(statistic: Statistic, entityType: EntityType, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic, material: Material) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic, material: Material, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic, entityType: EntityType) {
        throw AssertionError("player is offline")
    }

    override fun decrementStatistic(statistic: Statistic, entityType: EntityType, amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun setStatistic(statistic: Statistic, newValue: Int) {
        throw AssertionError("player is offline")
    }

    override fun setStatistic(statistic: Statistic, material: Material, newValue: Int) {
        throw AssertionError("player is offline")
    }

    override fun setStatistic(statistic: Statistic, entityType: EntityType, newValue: Int) {
        throw AssertionError("player is offline")
    }

    override fun getStatistic(statistic: Statistic): Int {
        throw AssertionError("player is offline")
    }

    override fun getStatistic(statistic: Statistic, material: Material): Int {
        throw AssertionError("player is offline")
    }

    override fun getStatistic(statistic: Statistic, entityType: EntityType): Int {
        throw AssertionError("player is offline")
    }

    override fun sendPluginMessage(source: Plugin, channel: String, message: ByteArray) {
        throw AssertionError("player is offline")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        throw AssertionError("player is offline")
    }

    override fun getDisplayName(): String {
        throw AssertionError("player is offline")
    }

    override fun setDisplayName(name: String?) {
        throw AssertionError("player is offline")
    }

    override fun getPlayerListName(): String {
        throw AssertionError("player is offline")
    }

    override fun setPlayerListName(name: String?) {
        throw AssertionError("player is offline")
    }

    override fun getPlayerListHeader(): String? {
        throw AssertionError("player is offline")
    }

    override fun getPlayerListFooter(): String? {
        throw AssertionError("player is offline")
    }

    override fun setPlayerListHeader(header: String?) {
        throw AssertionError("player is offline")
    }

    override fun setPlayerListFooter(footer: String?) {
        throw AssertionError("player is offline")
    }

    override fun setPlayerListHeaderFooter(header: String?, footer: String?) {
        throw AssertionError("player is offline")
    }

    override fun setCompassTarget(loc: Location) {
        throw AssertionError("player is offline")
    }

    override fun getCompassTarget(): Location {
        throw AssertionError("player is offline")
    }

    override fun getAddress(): InetSocketAddress? {
        throw AssertionError("player is offline")
    }

    override fun kickPlayer(message: String?) {
        throw AssertionError("player is offline")
    }

    override fun chat(msg: String) {
        throw AssertionError("player is offline")
    }

    override fun performCommand(command: String): Boolean {
        throw AssertionError("player is offline")
    }

    override fun isSneaking(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setSneaking(sneak: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isSprinting(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setSprinting(sprinting: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun saveData() {
        throw AssertionError("player is offline")
    }

    override fun loadData() {
        throw AssertionError("player is offline")
    }

    override fun setSleepingIgnored(isSleeping: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun isSleepingIgnored(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setBedSpawnLocation(location: Location?) {
        throw AssertionError("player is offline")
    }

    override fun setBedSpawnLocation(location: Location?, force: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun playNote(loc: Location, instrument: Byte, note: Byte) {
        throw AssertionError("player is offline")
    }

    override fun playNote(loc: Location, instrument: Instrument, note: Note) {
        throw AssertionError("player is offline")
    }

    override fun playSound(location: Location, sound: Sound, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(location: Location, sound: String, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(location: Location, sound: Sound, category: SoundCategory, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(location: Location, sound: String, category: SoundCategory, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(entity: Entity, sound: Sound, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(entity: Entity, sound: String, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(entity: Entity, sound: Sound, category: SoundCategory, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun playSound(entity: Entity, sound: String, category: SoundCategory, volume: Float, pitch: Float) {
        throw AssertionError("player is offline")
    }

    override fun stopSound(sound: Sound) {
        throw AssertionError("player is offline")
    }

    override fun stopSound(sound: String) {
        throw AssertionError("player is offline")
    }

    override fun stopSound(sound: Sound, category: SoundCategory?) {
        throw AssertionError("player is offline")
    }

    override fun stopSound(sound: String, category: SoundCategory?) {
        throw AssertionError("player is offline")
    }

    override fun stopSound(category: SoundCategory) {
        throw AssertionError("player is offline")
    }

    override fun stopAllSounds() {
        throw AssertionError("player is offline")
    }

    override fun breakBlock(block: Block): Boolean {
        throw AssertionError("player is offline")
    }

    override fun sendBlockChange(loc: Location, material: Material, data: Byte) {
        throw AssertionError("player is offline")
    }

    override fun sendBlockChange(loc: Location, block: BlockData) {
        throw AssertionError("player is offline")
    }

    override fun sendBlockChanges(blocks: MutableCollection<BlockState>, suppressLightUpdates: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun sendBlockDamage(loc: Location, progress: Float) {
        throw AssertionError("player is offline")
    }

    override fun sendEquipmentChange(entity: LivingEntity, slot: EquipmentSlot, item: ItemStack) {
        throw AssertionError("player is offline")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?) {
        throw AssertionError("player is offline")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?, dyeColor: DyeColor) {
        throw AssertionError("player is offline")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?, dyeColor: DyeColor, hasGlowingText: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun sendMap(map: MapView) {
        throw AssertionError("player is offline")
    }

    override fun updateInventory() {
        throw AssertionError("player is offline")
    }

    override fun getPreviousGameMode(): GameMode? {
        throw AssertionError("player is offline")
    }

    override fun setPlayerTime(time: Long, relative: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getPlayerTime(): Long {
        throw AssertionError("player is offline")
    }

    override fun getPlayerTimeOffset(): Long {
        throw AssertionError("player is offline")
    }

    override fun isPlayerTimeRelative(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun resetPlayerTime() {
        throw AssertionError("player is offline")
    }

    override fun setPlayerWeather(type: WeatherType) {
        throw AssertionError("player is offline")
    }

    override fun getPlayerWeather(): WeatherType? {
        throw AssertionError("player is offline")
    }

    override fun resetPlayerWeather() {
        throw AssertionError("player is offline")
    }

    override fun giveExp(amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun giveExpLevels(amount: Int) {
        throw AssertionError("player is offline")
    }

    override fun getExp(): Float {
        throw AssertionError("player is offline")
    }

    override fun setExp(exp: Float) {
        throw AssertionError("player is offline")
    }

    override fun getLevel(): Int {
        throw AssertionError("player is offline")
    }

    override fun setLevel(level: Int) {
        throw AssertionError("player is offline")
    }

    override fun getTotalExperience(): Int {
        throw AssertionError("player is offline")
    }

    override fun setTotalExperience(exp: Int) {
        throw AssertionError("player is offline")
    }

    override fun sendExperienceChange(progress: Float) {
        throw AssertionError("player is offline")
    }

    override fun sendExperienceChange(progress: Float, level: Int) {
        throw AssertionError("player is offline")
    }

    override fun getAllowFlight(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setAllowFlight(flight: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun hidePlayer(player: Player) {
        throw AssertionError("player is offline")
    }

    override fun hidePlayer(plugin: Plugin, player: Player) {
        throw AssertionError("player is offline")
    }

    override fun showPlayer(player: Player) {
        throw AssertionError("player is offline")
    }

    override fun showPlayer(plugin: Plugin, player: Player) {
        throw AssertionError("player is offline")
    }

    override fun canSee(player: Player): Boolean {
        throw AssertionError("player is offline")
    }

    override fun canSee(entity: Entity): Boolean {
        throw AssertionError("player is offline")
    }

    override fun hideEntity(plugin: Plugin, entity: Entity) {
        throw AssertionError("player is offline")
    }

    override fun showEntity(plugin: Plugin, entity: Entity) {
        throw AssertionError("player is offline")
    }

    override fun isFlying(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setFlying(value: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun setFlySpeed(value: Float) {
        throw AssertionError("player is offline")
    }

    override fun setWalkSpeed(value: Float) {
        throw AssertionError("player is offline")
    }

    override fun getFlySpeed(): Float {
        throw AssertionError("player is offline")
    }

    override fun getWalkSpeed(): Float {
        throw AssertionError("player is offline")
    }

    override fun setTexturePack(url: String) {
        throw AssertionError("player is offline")
    }

    override fun setResourcePack(url: String) {
        throw AssertionError("player is offline")
    }

    override fun setResourcePack(url: String, hash: ByteArray?) {
        throw AssertionError("player is offline")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, prompt: String?) {
        throw AssertionError("player is offline")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, force: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, prompt: String?, force: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun getScoreboard(): Scoreboard {
        throw AssertionError("player is offline")
    }

    override fun setScoreboard(scoreboard: Scoreboard) {
        throw AssertionError("player is offline")
    }

    override fun getWorldBorder(): WorldBorder? {
        throw AssertionError("player is offline")
    }

    override fun setWorldBorder(border: WorldBorder?) {
        throw AssertionError("player is offline")
    }

    override fun isHealthScaled(): Boolean {
        throw AssertionError("player is offline")
    }

    override fun setHealthScaled(scale: Boolean) {
        throw AssertionError("player is offline")
    }

    override fun setHealthScale(scale: Double) {
        throw AssertionError("player is offline")
    }

    override fun getHealthScale(): Double {
        throw AssertionError("player is offline")
    }

    override fun getSpectatorTarget(): Entity? {
        throw AssertionError("player is offline")
    }

    override fun setSpectatorTarget(entity: Entity?) {
        throw AssertionError("player is offline")
    }

    override fun sendTitle(title: String?, subtitle: String?) {
        throw AssertionError("player is offline")
    }

    override fun sendTitle(title: String?, subtitle: String?, fadeIn: Int, stay: Int, fadeOut: Int) {
        throw AssertionError("player is offline")
    }

    override fun resetTitle() {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(particle: Particle, location: Location, count: Int) {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(particle: Particle, x: Double, y: Double, z: Double, count: Int) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(particle: Particle, location: Location, count: Int, data: T?) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(particle: Particle, x: Double, y: Double, z: Double, count: Int, data: T?) {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
    ) {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
    ) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        data: T?,
    ) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        data: T?,
    ) {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
    ) {
        throw AssertionError("player is offline")
    }

    override fun spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
    ) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?,
    ) {
        throw AssertionError("player is offline")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?,
    ) {
        throw AssertionError("player is offline")
    }

    override fun getAdvancementProgress(advancement: Advancement): AdvancementProgress {
        throw AssertionError("player is offline")
    }

    override fun getClientViewDistance(): Int {
        throw AssertionError("player is offline")
    }

    override fun getPing(): Int {
        throw AssertionError("player is offline")
    }

    override fun getLocale(): String {
        throw AssertionError("player is offline")
    }

    override fun updateCommands() {
        throw AssertionError("player is offline")
    }

    override fun openBook(book: ItemStack) {
        throw AssertionError("player is offline")
    }

    override fun openSign(sign: Sign) {
        throw AssertionError("player is offline")
    }

    override fun showDemoScreen() {
        throw AssertionError("player is offline")
    }

    override fun isAllowingServerListings(): Boolean {
        throw AssertionError("player is offline")
    }
}