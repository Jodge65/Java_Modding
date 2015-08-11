package fr.Jodge.jodgeLibrary.common.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

import fr.Jodge.jodgeLibrary.common.block.JFallingBlock;
import fr.Jodge.jodgeLibrary.common.function.JLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JEntityFallingBlock extends EntityFallingBlock
{
	    private IBlockState fallTile;
	    public int fallTime;
	    public boolean shouldDropItem;
	    private boolean field_145808_f;
	    private boolean hurtEntities;
	    private int fallHurtMax = 40;
	    private float fallHurtAmount;
	    public NBTTagCompound tileEntityData;
	    
	    public boolean canDownOnLiquid = true;

	    public boolean canCollide;
	    
		public JEntityFallingBlock(World worldIn, BlockPos pos, IBlockState fallingBlockState)
		{
			this(worldIn, pos.getX(), pos.getY(), pos.getZ(), fallingBlockState);
		}
		public JEntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState)
		{
			this(worldIn, x, y, z, fallingBlockState, 2.0F, true);
		}
		public JEntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState, float hurtDamage, boolean shouldDrop)
		{
			this(worldIn, x, y, z, fallingBlockState, 2.0F, true, false);

		}
		public JEntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState, float hurtDamage, boolean shouldDrop, boolean forceNoCollide)
		{
	        super(worldIn);
	        fallTile = fallingBlockState;
	        preventEntitySpawning = true;
	        setSize(0.98F, 0.98F);
	        setPosition(x, y, z);
	        motionX = 0.0D;
	        motionY = 0.0D;
	        motionZ = 0.0D;
	        prevPosX = x;
	        prevPosY = y;
	        prevPosZ = z;	
		    shouldDropItem = shouldDrop;
		    fallHurtAmount = hurtDamage;
		    canCollide = forceNoCollide;
		}

		public JEntityFallingBlock CanDownOnWater(boolean canDownOnWater)
		{
			canDownOnLiquid = canDownOnWater;
			return this;
		}
	    /**
	     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	     * prevent them from trampling crops
	     */
	    protected boolean canTriggerWalking()
	    {
	        return false;
	    }

	    protected void entityInit() {}

	    /**
	     * Returns true if other Entities should be prevented from moving through this Entity.
	     */
	    public boolean canBeCollidedWith()
	    {
	        return (canCollide)||(!canCollide && !isDead);
	    }

	    /**
	     * Called to update the entity's position/logic.
	     */
	    public void onUpdate()
	    {
	        Block block = fallTile.getBlock();

	        if (block.getMaterial() == Material.air)
	        {
	            setDead();
	        }
	        else
	        {
	            prevPosX = posX;
	            prevPosY = posY;
	            prevPosZ = posZ;
	            BlockPos blockpos;

	            if (fallTime++ == 0)
	            {
	                blockpos = new BlockPos(this);

	                if (worldObj.getBlockState(blockpos).getBlock() == block)
	                {
	                    worldObj.setBlockToAir(blockpos);
	                }
	                else if (!worldObj.isRemote)
	                {
	                    setDead();
	                    return;
	                }
	            }

	            motionY -= 0.03999999910593033D;
	            moveEntity(motionX, motionY, motionZ);
	            motionX *= 0.9800000190734863D;
	            motionY *= 0.9800000190734863D;
	            motionZ *= 0.9800000190734863D;

	            if (!worldObj.isRemote)
	            {
	                blockpos = new BlockPos(this);

	                if (onGround)
	                {
	                    motionX *= 0.699999988079071D;
	                    motionZ *= 0.699999988079071D;
	                    motionY *= -0.5D;

	                    if (worldObj.getBlockState(blockpos).getBlock() != Blocks.piston_extension && !( isInWater() && canDownOnLiquid ) )
	                    {
	                        setDead();
	                        
	                        if (	!field_145808_f && 
	                        		worldObj.canBlockBePlaced(block, blockpos, true, EnumFacing.UP, (Entity)null, (ItemStack)null) &&
	                        		!JFallingBlock.canFall(worldObj, blockpos.down(), (JFallingBlock) block) && 
	                        		JFallingBlock.canFall(worldObj, blockpos, (JFallingBlock) block) && 
	                        		worldObj.setBlockState(blockpos, fallTile, 3))
	                        {
	                            if (block instanceof BlockFalling)
	                            {
	                                ((BlockFalling)block).onEndFalling(worldObj, blockpos);
	                            }

	                            if (tileEntityData != null && block instanceof ITileEntityProvider)
	                            {
	                                TileEntity tileentity = worldObj.getTileEntity(blockpos);

	                                if (tileentity != null)
	                                {
	                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
	                                    tileentity.writeToNBT(nbttagcompound);
	                                    Iterator iterator = tileEntityData.getKeySet().iterator();

	                                    while (iterator.hasNext())
	                                    {
	                                        String s = (String)iterator.next();
	                                        NBTBase nbtbase = tileEntityData.getTag(s);

	                                        if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
	                                        {
	                                            nbttagcompound.setTag(s, nbtbase.copy());
	                                        }
	                                    }

	                                    tileentity.readFromNBT(nbttagcompound);
	                                    tileentity.markDirty();
	                                }
	                            }
	                        }
	                        else if (shouldDropItem && !field_145808_f && worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"))
	                        {
	                            entityDropItem(new ItemStack(block, 1, block.damageDropped(fallTile)), 0.0F);
	                        }
	                    }
	                }
	                else if (fallTime > 100 && !worldObj.isRemote && (blockpos.getY() < 1 || blockpos.getY() > 256) || fallTime > 600)
	                {
	                    if (shouldDropItem && worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"))
	                    {
	                        entityDropItem(new ItemStack(block, 1, block.damageDropped(fallTile)), 0.0F);
	                    }

	                    setDead();
	                }
	            }
	        }
	    }

	    public void fall(float distance, float damageMultiplier)
	    {
	        Block block = fallTile.getBlock();

	        if (hurtEntities)
	        {
	            int i = MathHelper.ceiling_float_int(distance - 1.0F);

	            if (i > 0)
	            {
	                ArrayList arraylist = Lists.newArrayList(worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
	                boolean flag = block == Blocks.anvil;
	                DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
	                Iterator iterator = arraylist.iterator();

	                while (iterator.hasNext())
	                {
	                    Entity entity = (Entity)iterator.next();
	                    entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor_float((float)i * fallHurtAmount), fallHurtMax));
	                }

	                if (flag && (double)rand.nextFloat() < 0.05000000074505806D + (double)i * 0.05D)
	                {
	                    int j = ((Integer)fallTile.getValue(BlockAnvil.DAMAGE)).intValue();
	                    ++j;

	                    if (j > 2)
	                    {
	                        field_145808_f = true;
	                    }
	                    else
	                    {
	                        fallTile = fallTile.withProperty(BlockAnvil.DAMAGE, Integer.valueOf(j));
	                    }
	                }
	            }
	        }
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    protected void writeEntityToNBT(NBTTagCompound tagCompound)
	    {
	        Block block = fallTile != null ? fallTile.getBlock() : Blocks.air;
	        ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
	        tagCompound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
	        tagCompound.setByte("Data", (byte)block.getMetaFromState(fallTile));
	        tagCompound.setByte("Time", (byte)fallTime);
	        tagCompound.setBoolean("DropItem", shouldDropItem);
	        tagCompound.setBoolean("HurtEntities", hurtEntities);
	        tagCompound.setFloat("FallHurtAmount", fallHurtAmount);
	        tagCompound.setInteger("FallHurtMax", fallHurtMax);

	        if (tileEntityData != null)
	        {
	            tagCompound.setTag("TileEntityData", tileEntityData);
	        }
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    protected void readEntityFromNBT(NBTTagCompound tagCompund)
	    {
	        int i = tagCompund.getByte("Data") & 255;

	        if (tagCompund.hasKey("Block", 8))
	        {
	            fallTile = Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(i);
	        }
	        else if (tagCompund.hasKey("TileID", 99))
	        {
	            fallTile = Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(i);
	        }
	        else
	        {
	            fallTile = Block.getBlockById(tagCompund.getByte("Tile") & 255).getStateFromMeta(i);
	        }

	        fallTime = tagCompund.getByte("Time") & 255;
	        Block block = fallTile.getBlock();

	        if (tagCompund.hasKey("HurtEntities", 99))
	        {
	            hurtEntities = tagCompund.getBoolean("HurtEntities");
	            fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
	            fallHurtMax = tagCompund.getInteger("FallHurtMax");
	        }
	        else if (block == Blocks.anvil)
	        {
	            hurtEntities = true;
	        }

	        if (tagCompund.hasKey("DropItem", 99))
	        {
	            shouldDropItem = tagCompund.getBoolean("DropItem");
	        }

	        if (tagCompund.hasKey("TileEntityData", 10))
	        {
	            tileEntityData = tagCompund.getCompoundTag("TileEntityData");
	        }

	        if (block == null || block.getMaterial() == Material.air)
	        {
	            fallTile = Blocks.sand.getDefaultState();
	        }
	    }

	    public void setHurtEntities(boolean p_145806_1_)
	    {
	        hurtEntities = p_145806_1_;
	    }

	    public void addEntityCrashInfo(CrashReportCategory category)
	    {
	        super.addEntityCrashInfo(category);

	        if (fallTile != null)
	        {
	            Block block = fallTile.getBlock();
	            category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
	            category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(fallTile)));
	        }
	    }

	    @SideOnly(Side.CLIENT)
	    public World getWorldObj()
	    {
	        return worldObj;
	    }

	    /**
	     * Return whether this entity should be rendered as on fire.
	     */
	    @SideOnly(Side.CLIENT)
	    public boolean canRenderOnFire()
	    {
	        return true;
	    }

	    public IBlockState getBlock()
	    {
	        return fallTile;
	    }	
}