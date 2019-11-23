package gregtech.api.metatileentity.implementations;

import cpw.mods.fml.common.Loader;
import gregtech.api.enums.*;
import gregtech.api.gui.GT_Container_BasicMachine;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Collectors;

import static gregtech.api.enums.GT_Values.*;
import static gregtech.api.enums.OrePrefixes.*;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public class GT_MetaTileEntity_BasicMachine_GT_Recipe extends GT_MetaTileEntity_BasicMachine {
    private final GT_Recipe.GT_Recipe_Map mRecipes;
    private final int mTankCapacity, mSpecialEffect;
    private final String mSound;
    private final boolean mSharedTank, mRequiresFluidForFiltering;
    private final byte mGUIParameterA, mGUIParameterB;
    public static final OrePrefixes[] PLATEBENDER_INPUT_OREPREFIXES = {plate, plateDouble, plateTriple, ingot, ingotDouble, ingotTriple, ingotQuadruple, ingotQuintuple};

    public GT_MetaTileEntity_BasicMachine_GT_Recipe(String aName, int aTier, String aDescription, GT_Recipe.GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int aTankCapacity, int aAmperage, int aGUIParameterA, int aGUIParameterB, ITexture[][][] aTextures, String aGUIName, String aNEIName, String aSound, boolean aSharedTank, boolean aRequiresFluidForFiltering, int aSpecialEffect) {
        super(aName, aTier, aAmperage, aDescription, aTextures, aInputSlots, aOutputSlots, aGUIName, aNEIName);
        this.mSharedTank = aSharedTank;
        this.mTankCapacity = aTankCapacity;
        this.mSpecialEffect = aSpecialEffect;
        this.mRequiresFluidForFiltering = aRequiresFluidForFiltering;
        this.mRecipes = aRecipes;
        this.mSound = aSound;
        this.mGUIParameterA = (byte) aGUIParameterA;
        this.mGUIParameterB = (byte) aGUIParameterB;
    }

    public GT_MetaTileEntity_BasicMachine_GT_Recipe(String aName, int aTier, String[] aDescription, GT_Recipe.GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int aTankCapacity, int aAmperage, int aGUIParameterA, int aGUIParameterB, ITexture[][][] aTextures, String aGUIName, String aNEIName, String aSound, boolean aSharedTank, boolean aRequiresFluidForFiltering, int aSpecialEffect) {
        super(aName, aTier, aAmperage, aDescription, aTextures, aInputSlots, aOutputSlots, aGUIName, aNEIName);
        this.mSharedTank = aSharedTank;
        this.mTankCapacity = aTankCapacity;
        this.mSpecialEffect = aSpecialEffect;
        this.mRequiresFluidForFiltering = aRequiresFluidForFiltering;
        this.mRecipes = aRecipes;
        this.mSound = aSound;
        this.mGUIParameterA = (byte) aGUIParameterA;
        this.mGUIParameterB = (byte) aGUIParameterB;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_BasicMachine_GT_Recipe(this.mName, this.mTier, this.mDescriptionArray, this.mRecipes, this.mInputSlotCount, this.mOutputItems == null ? 0 : this.mOutputItems.length, this.mTankCapacity, this.mAmperage, this.mGUIParameterA, this.mGUIParameterB, this.mTextures, this.mGUIName, this.mNEIName, this.mSound, this.mSharedTank, this.mRequiresFluidForFiltering, this.mSpecialEffect);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(), this.mGUIName, GT_Utility.isStringValid(this.mNEIName) ? this.mNEIName : this.getRecipeList() != null ? this.getRecipeList().mUnlocalizedName : "", this.mGUIParameterA, this.mGUIParameterB);
    }

    public static final OrePrefixes[] MACERATOR_INPUT_OREPREFIXES = {ore, oreBlackgranite, oreEndstone, oreEnd, oreNetherrack, oreNether, oreRedgranite, oreRich, oreSmall, oreNormal, oreDense, orePoor, oreMarble, oreGem, oreBasalt};
    private Set<GT_Recipe> gt_recipes_cache = new HashSet<>();

    public GT_MetaTileEntity_BasicMachine_GT_Recipe(int aID, String aName, String aNameRegional, int aTier, String aDescription, GT_Recipe.GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int aTankCapacity, int aGUIParameterA, int aGUIParameterB, String aGUIName, String aSound, boolean aSharedTank, boolean aRequiresFluidForFiltering, int aSpecialEffect, String aOverlays, Object[] aRecipe) {
        super(aID, aName, aNameRegional, aTier, aRecipes.mAmperage, aDescription, aInputSlots, aOutputSlots, aGUIName, aRecipes.mNEIName, new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_SIDE_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_SIDE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_FRONT")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_TOP_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_TOP")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_BOTTOM_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_BOTTOM")));
        this.mSharedTank = aSharedTank;
        this.mTankCapacity = aTankCapacity;
        this.mSpecialEffect = aSpecialEffect;
        this.mRequiresFluidForFiltering = aRequiresFluidForFiltering;
        this.mRecipes = aRecipes;
        this.mSound = aSound;
        this.mGUIParameterA = (byte) aGUIParameterA;
        this.mGUIParameterB = (byte) aGUIParameterB;


        //TODO: CHECK
        if (aRecipe != null) {
            for (int i = 3; i < aRecipe.length; i++) {
                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CIRCUIT) {
                    aRecipe[i] = Tier.ELECTRIC[this.mTier].mManagingObject;
                    continue;
                }
                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.BETTER_CIRCUIT) {
                    aRecipe[i] = Tier.ELECTRIC[this.mTier].mBetterManagingObject;
                    continue;
                }
                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.HULL) {
                    aRecipe[i] = Tier.ELECTRIC[this.mTier].mHullObject;
                    continue;
                }
                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.WIRE) {
                    aRecipe[i] = Tier.ELECTRIC[this.mTier].mConductingObject;
                    continue;
                }
                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.WIRE4) {
                    aRecipe[i] = Tier.ELECTRIC[this.mTier].mLargerConductingObject;
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.GLASS) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            aRecipe[i] = new ItemStack(Blocks.glass, 1, W);
                            break;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            if (Loader.isModLoaded("bartworks")) {
                                aRecipe[i] = "blockGlass"+VN[aTier];
                                break;
                            }
                        default:
                            if (Loader.isModLoaded("bartworks")) {
                                aRecipe[i] = "blockGlass"+VN[8];
                                break;
                            } else {
                                aRecipe[i] = Ic2Items.reinforcedGlass;
                                break;
                            }
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PLATE) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = plate.get(Materials.Steel);
                            break;
                        case 2:
                            aRecipe[i] = plate.get(Materials.Aluminium);
                            break;
                        case 3:
                            aRecipe[i] = plate.get(Materials.StainlessSteel);
                            break;
                        case 4:
                            aRecipe[i] = plate.get(Materials.Titanium);
                            break;
                        case 5:
                            aRecipe[i] = plate.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = plate.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = plate.get(Materials.HSSE);
                            break;
                        default:
                            aRecipe[i] = plate.get(Materials.Neutronium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PIPE) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = pipeMedium.get(Materials.Bronze);
                            break;
                        case 2:
                            aRecipe[i] = pipeMedium.get(Materials.Steel);
                            break;
                        case 3:
                            aRecipe[i] = pipeMedium.get(Materials.StainlessSteel);
                            break;
                        case 4:
                            aRecipe[i] = pipeMedium.get(Materials.Titanium);
                            break;
                        case 5:
                            aRecipe[i] = pipeMedium.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = pipeSmall.get(Materials.Ultimate);
                            break;
                        case 7:
                            aRecipe[i] = pipeMedium.get(Materials.Ultimate);
                            break;
                        case 8:
                            aRecipe[i] = pipeLarge.get(Materials.Ultimate);
                            break;
                        default:
                            aRecipe[i] = pipeHuge.get(Materials.Ultimate);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.COIL_HEATING) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = wireGt02.get(Materials.AnyCopper);
                            break;
                        case 2:
                            aRecipe[i] = wireGt02.get(Materials.Cupronickel);
                            break;
                        case 3:
                            aRecipe[i] = wireGt02.get(Materials.Kanthal);
                            break;
                        case 4:
                            aRecipe[i] = wireGt02.get(Materials.Nichrome);
                            break;
                        case 5:
                            aRecipe[i] = wireGt02.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = wireGt02.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = wireGt02.get(Materials.Naquadah);
                            break;
                        case 8:
                            aRecipe[i] = wireGt02.get(Materials.NaquadahAlloy);
                            break;
                        case 9:
                            aRecipe[i] = wireGt04.get(Materials.NaquadahAlloy);
                            break;
                        default:
                            aRecipe[i] = wireGt08.get(Materials.NaquadahAlloy);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.COIL_HEATING_DOUBLE) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = wireGt04.get(Materials.AnyCopper);
                            break;
                        case 2:
                            aRecipe[i] = wireGt04.get(Materials.Cupronickel);
                            break;
                        case 3:
                            aRecipe[i] = wireGt04.get(Materials.Kanthal);
                            break;
                        case 4:
                            aRecipe[i] = wireGt04.get(Materials.Nichrome);
                            break;
                        case 5:
                            aRecipe[i] = wireGt04.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = wireGt04.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = wireGt04.get(Materials.Naquadah);
                            break;
                        case 8:
                            aRecipe[i] = wireGt04.get(Materials.NaquadahAlloy);
                            break;
                        case 9:
                            aRecipe[i] = wireGt08.get(Materials.NaquadahAlloy);
                            break;
                        default:
                            aRecipe[i] = wireGt16.get(Materials.NaquadahAlloy);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_DISTILLATION) {
                    switch (this.mTier) {
                        default:
                            aRecipe[i] = stick.get(Materials.Blaze);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_MAGNETIC) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = stick.get(Materials.IronMagnetic);
                            break;
                        case 2:
                        case 3:
                            aRecipe[i] = stick.get(Materials.SteelMagnetic);
                            break;
                        case 4:
                        case 5:
                            aRecipe[i] = stick.get(Materials.NeodymiumMagnetic);
                            break;
                        case 6:
                        case 7:
                            aRecipe[i] = stick.get(Materials.SamariumMagnetic);
                            break;
                        default:
                            aRecipe[i] = stickLong.get(Materials.SamariumMagnetic);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.STICK_ELECTROMAGNETIC) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = stick.get(Materials.AnyIron);
                            break;
                        case 2:
                        case 3:
                            aRecipe[i] = stick.get(Materials.Steel);
                            break;
                        case 4:
                            aRecipe[i] = stick.get(Materials.Neodymium);
                            break;
                        default:
                            aRecipe[i] = stick.get(Materials.VanadiumGallium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.COIL_ELECTRIC) {
                    switch (this.mTier) {
                        case 0:
                            aRecipe[i] = wireGt01.get(Materials.Lead);
                            break;
                        case 1:
                            aRecipe[i] = wireGt02.get(Materials.Tin);
                            break;
                        case 2:
                            aRecipe[i] = wireGt02.get(Materials.AnyCopper);
                            break;
                        case 3:
                            aRecipe[i] = wireGt04.get(Materials.AnyCopper);
                            break;
                        case 4:
                            aRecipe[i] = wireGt08.get(Materials.AnnealedCopper);
                            break;
                        case 5:
                            aRecipe[i] = wireGt16.get(Materials.AnnealedCopper);
                            break;
                        case 6:
                            aRecipe[i] = wireGt04.get(Materials.YttriumBariumCuprate);
                            break;
                        case 7:
                            aRecipe[i] = wireGt08.get(Materials.Iridium);
                            break;
                        default:
                            aRecipe[i] = wireGt16.get(Materials.Osmium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.ROBOT_ARM) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Robot_Arm_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Robot_Arm_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Robot_Arm_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Robot_Arm_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Robot_Arm_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Robot_Arm_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Robot_Arm_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Robot_Arm_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Robot_Arm_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Robot_Arm_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PUMP) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Pump_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Pump_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Pump_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Pump_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Pump_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Pump_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Pump_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Electric_Pump_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Electric_Pump_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Pump_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.ROTOR) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = rotor.get(Materials.Tin);
                            break;
                        case 2:
                            aRecipe[i] = rotor.get(Materials.Bronze);
                            break;
                        case 3:
                            aRecipe[i] = rotor.get(Materials.Steel);
                            break;
                        case 4:
                            aRecipe[i] = rotor.get(Materials.StainlessSteel);
                            break;
                        case 5:
                            aRecipe[i] = rotor.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = rotor.get(Materials.Chrome);
                            break;
                        case 7:
                            aRecipe[i] = rotor.get(Materials.Iridium);
                            break;
                        default:
                            aRecipe[i] = rotor.get(Materials.Osmium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.MOTOR) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Motor_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Motor_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Motor_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Motor_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Motor_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Motor_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Motor_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Electric_Motor_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Electric_Motor_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Motor_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.PISTON) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Piston_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Piston_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Piston_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Piston_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Piston_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Piston_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Piston_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Electric_Piston_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Electric_Piston_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Piston_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.CONVEYOR) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Conveyor_Module_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Conveyor_Module_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Conveyor_Module_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Conveyor_Module_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Conveyor_Module_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Conveyor_Module_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Conveyor_Module_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Conveyor_Module_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Conveyor_Module_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Conveyor_Module_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.EMITTER) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Emitter_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Emitter_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Emitter_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Emitter_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Emitter_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Emitter_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Emitter_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Emitter_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Emitter_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Emitter_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.SENSOR) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Sensor_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Sensor_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Sensor_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Sensor_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Sensor_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Sensor_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Sensor_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Sensor_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Sensor_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Sensor_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == GT_MetaTileEntity_BasicMachine_GT_Recipe.X.FIELD_GENERATOR) {
                    switch (this.mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Field_Generator_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Field_Generator_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Field_Generator_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Field_Generator_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Field_Generator_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Field_Generator_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Field_Generator_ZPM;
                            break;
                        case 8:
                            aRecipe[i] = ItemList.Field_Generator_UV;
                            break;
                        case 9:
                            aRecipe[i] = ItemList.Field_Generator_UHV;
                            break;
                        default:
                            aRecipe[i] = ItemList.Field_Generator_UEV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] instanceof GT_MetaTileEntity_BasicMachine_GT_Recipe.X)
                    throw new IllegalArgumentException("MISSING TIER MAPPING FOR: " + aRecipe[i] + " AT TIER " + mTier);
            }

            if (!GT_ModHandler.addCraftingRecipe(getStackForm(1), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, aRecipe)) {
                throw new IllegalArgumentException("INVALID CRAFTING RECIPE FOR: " + getStackForm(1).getDisplayName());
            }
        }
    }

    public static GT_Recipe abstractRecipeCalculator(GT_MetaTileEntity_BasicMachine metaTileEntity) {
        return abstractRecipeCalculator(metaTileEntity.getRecipeList(), metaTileEntity.getAllInputs());
    }

    public static GT_Recipe abstractRecipeCalculator(GT_Recipe.GT_Recipe_Map recipe_map, ItemStack[] inputs) {
        switch (recipe_map.mUnlocalizedName) {
            case "gt.recipe.metalbender": {
                for (OrePrefixes theorethical : PLATEBENDER_INPUT_OREPREFIXES) {
                    GT_Recipe recipe = getPlateBenderRecipe(recipe_map, theorethical, inputs);
                    if (recipe != null)
                        return recipe;
                }
                return null;
            }
            case "gt.recipe.macerator": {
                for (OrePrefixes theorethical : MACERATOR_INPUT_OREPREFIXES) {
                    GT_Recipe recipe = getMaceratorRecipe(theorethical, inputs);
                    if (recipe != null)
                        return recipe;
                }
                return null;
            }
            case "gt.recipe.hammer": {
                for (OrePrefixes theorethical : MACERATOR_INPUT_OREPREFIXES) {
                    GT_Recipe recipe = getForgeHammerRecipe(theorethical, inputs);
                    if (recipe != null)
                        return recipe;
                }
                return null;
            }
            case "gt.recipe.extruder": {
                GT_Recipe recipe = getExtruderRecipe(ingot, inputs);
                return recipe;
            }
            default:
                return null;
        }
    }

    private static GT_Recipe getExtruderRecipe(OrePrefixes aPrefix, ItemStack[] inputs) {
        List<ItemStack> stackList = Arrays.stream(inputs)
                .filter(c -> GT_OreDictUnificator.getAssociation(c) != null)
                .filter(c -> GT_OreDictUnificator.getAssociation(c).mPrefix == aPrefix)
                .collect(Collectors.toList());
        List<ItemStack> shapeList = Arrays.stream(inputs)
                .filter(c -> ItemList.Shape_Extruder_Casing.get(1).getItem().equals(c.getItem()) && c.getItemDamage() >= 32350 && c.getItemDamage() <= 32376)
                .collect(Collectors.toList());

        if (shapeList.isEmpty() || stackList.isEmpty())
            return null;
        ItemStack shape = shapeList.get(0).copy();
        shape.stackSize = 0;
        OrePrefixes outgoingPrefix;
        switch (shape.getItemDamage()) {
            case 32350:
                outgoingPrefix = plate;
                break;
            case 32351:
                outgoingPrefix = stick;
                break;
            case 32352:
                outgoingPrefix = bolt;
                break;
            case 32353:
                outgoingPrefix = ring;
                break;
            case 32354:
                outgoingPrefix = cell;
                break;
            case 32355:
                outgoingPrefix = ingot;
                break;
            case 32356:
                outgoingPrefix = wireGt01;
                break;
            case 32357:
                outgoingPrefix = itemCasing;
                break;
            case 32358:
                outgoingPrefix = pipeTiny;
                break;
            case 32359:
                outgoingPrefix = pipeSmall;
                break;
            case 32360:
                outgoingPrefix = pipeMedium;
                break;
            case 32361:
                outgoingPrefix = pipeLarge;
                break;
            case 32362:
                outgoingPrefix = pipeHuge;
                break;
            case 32363:
                outgoingPrefix = block;
                break;
            default:
                return null;
        }

        ItemStack inputStack = stackList.get(0).copy().splitStack(Math.max((int) (outgoingPrefix.mMaterialAmount / ingot.mMaterialAmount), 1));
        Materials notSmeltIntoMaterial = GT_OreDictUnificator.getAssociation(inputStack).mMaterial.mMaterial;
        Materials aMaterial = notSmeltIntoMaterial.mSmeltInto;

        long aMaterialMass = aMaterial.getMass();
        int tVoltageMultiplier = aMaterial.mBlastFurnaceTemp >= 2800 ? 60 : 15;
        int tAmount = Math.max((int) (ingot.mMaterialAmount / outgoingPrefix.mMaterialAmount), 1);
        ItemStack aStack = GT_OreDictUnificator.get(outgoingPrefix, aMaterial, tAmount);
        if (aStack == null)
            return null;
        if (aMaterial.contains(SubTag.NO_SMASHING)) {
            tVoltageMultiplier /= 4;
        } else if (aPrefix.name().startsWith(OrePrefixes.dust.name())) {
            return null;
        }

        switch (outgoingPrefix) {

            case stick:
            case ring:
            case wireGt01:
                return buildExtruderRecipe(shape, inputStack, aStack, (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
            case block:
                return !outgoingPrefix.isIgnored(aMaterial) ? buildExtruderRecipe(shape, inputStack, aStack, 10 * tAmount, 8 * tVoltageMultiplier) : null;
            case bolt:
                return buildExtruderRecipe(shape, inputStack, aStack, (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
            case plate:
                return buildExtruderRecipe(shape, inputStack, aStack, (int) Math.max(aMaterialMass * tAmount, tAmount), 8 * tVoltageMultiplier);

        }
        return null;
    }

    private static GT_Recipe buildExtruderRecipe(ItemStack shape, ItemStack inputStack, ItemStack aStack, int time, int eu) {
        return new GT_Recipe(true, new ItemStack[]{shape, inputStack},
                new ItemStack[]{aStack},
                null, null, null, null,
                time, eu, 0);
    }

    private static GT_Recipe getPlateBenderRecipe(GT_Recipe.GT_Recipe_Map recipe_map, OrePrefixes inputPrefix, ItemStack[] inputs) {
        int circuitConfig = 25;
        for (ItemStack stack : inputs) {
            if (stack != null && stack.getItem().equals(GT_Utility.getIntegratedCircuit(0).getItem()))
                circuitConfig = stack.getItemDamage();
        }
        if (circuitConfig == 25)
            return null;

        List<ItemStack> stackSet = Arrays.stream(inputs)
                .filter(c -> GT_OreDictUnificator.getAssociation(c) != null)
                .filter(c -> GT_OreDictUnificator.getAssociation(c).mPrefix == inputPrefix)
                .collect(Collectors.toList());
        if (stackSet.isEmpty())
            return null;
        ItemStack stack = stackSet.get(0).copy().splitStack(circuitConfig);

        Materials materials = GT_OreDictUnificator.getAssociation(stack).mMaterial.mMaterial;
        if (materials.contains(SubTag.NO_SMASHING))
            return null;
        OrePrefixes prefix = prefixFromCircuitConfiguration(recipe_map, inputPrefix, circuitConfig);
        if (prefix == null)
            return null;
        ItemStack output = GT_OreDictUnificator.get(prefix, materials, prefix == foil ? 4 : 1);
        if (output == null)
            return null;
        return new GT_Recipe(true, new ItemStack[]{stack, ItemList.Circuit_Integrated.getWithDamage(0, circuitConfig)}, new ItemStack[]{output}, null, null, null, null, (int) Math.max(materials.getMass() * circuitConfig, 1), circuitConfig == 1 && (prefix == plate || prefix == foil) ? 24 : 96, 0);
    }

    private static GT_Recipe getForgeHammerRecipe(OrePrefixes aPrefix, ItemStack[] inputs) {
        if (getMaceratorRecipe(aPrefix, inputs) == null)
            return null;
        ItemStack inputOre = getMaceratorRecipe(aPrefix, inputs).mInputs[0].copy();
        ItemStack tCrushed = getMaceratorRecipe(aPrefix, inputs).mOutputs[0].copy();
        Materials aMaterial = GT_OreDictUnificator.getAssociation(inputOre).mMaterial.mMaterial;
        Materials tMaterial = aMaterial.mOreReplacement;
        ItemStack tGem = GT_OreDictUnificator.get(gem, tMaterial, 1L);
        boolean tIsRich = (aPrefix == oreNetherrack) || (aPrefix == oreNether) || (aPrefix == oreEndstone) || (aPrefix == oreEnd) || (aPrefix == oreRich) || (aPrefix == oreDense);
        int aMultiplier = tIsRich ? 2 : 1;
        tCrushed.stackSize = aMultiplier;
        return new GT_Recipe(true, new ItemStack[]{inputOre}, new ItemStack[]{GT_Utility.copy(GT_Utility.copyAmount(tCrushed.stackSize, tGem), tCrushed)}, null, null, null, null, 16, 10, 0);
    }

    private static GT_Recipe getMaceratorRecipe(OrePrefixes aPrefix, ItemStack[] inputs) {

        List<ItemStack> stackSet = Arrays.stream(inputs)
                .filter(c -> GT_OreDictUnificator.getAssociation(c) != null)
                .filter(c -> GT_OreDictUnificator.getAssociation(c).mPrefix == aPrefix)
                .collect(Collectors.toList());
        if (stackSet.isEmpty())
            return null;
        ItemStack stack = stackSet.get(0).copy().splitStack(1);

        Materials aMaterial = GT_OreDictUnificator.getAssociation(stack).mMaterial.mMaterial;
        Materials tMaterial = aMaterial.mOreReplacement;
        boolean tIsRich = (aPrefix == oreNetherrack) || (aPrefix == oreNether) || (aPrefix == oreEndstone) || (aPrefix == oreEnd) || (aPrefix == oreRich) || (aPrefix == oreDense);

        int aMultiplier = tIsRich ? 2 : 1;
        ItemStack aOreStack = GT_Utility.copyAmount(1L, stack);
        aOreStack.stackSize = 1;

        ItemStack tIngot = GT_OreDictUnificator.get(ingot, aMaterial.mDirectSmelting, 1L);
        ItemStack tGem = GT_OreDictUnificator.get(gem, tMaterial, 1L);
        ItemStack tSmeltInto = tIngot == null ? null : aMaterial.contains(SubTag.SMELTING_TO_GEM) ? GT_OreDictUnificator.get(gem, tMaterial.mDirectSmelting, GT_OreDictUnificator.get(crystal, tMaterial.mDirectSmelting, GT_OreDictUnificator.get(gem, tMaterial, GT_OreDictUnificator.get(crystal, tMaterial, 1L), 1L), 1L), 1L) : tIngot;

        ItemStack tDust = GT_OreDictUnificator.get(dust, tMaterial, tGem, 1L);
        ItemStack tCleaned = GT_OreDictUnificator.get(crushedPurified, tMaterial, tDust, 1L);
        ItemStack tCrushed = GT_OreDictUnificator.get(crushed, tMaterial, aMaterial.mOreMultiplier * aMultiplier);
        ItemStack aOutput2 = tMaterial.contains(SubTag.PULVERIZING_CINNABAR) ? GT_OreDictUnificator.get(crystal, Materials.Cinnabar, GT_OreDictUnificator.get(gem, tMaterial, GT_Utility.copyAmount(1L, tDust), 1L), 1L) : GT_OreDictUnificator.get(gem, tMaterial, GT_Utility.copyAmount(1L, tDust), 1L);
        int aChance2 = tDust == null ? 0 : tDust.stackSize * 10 * aMultiplier * aMaterial.mByProductMultiplier;
        if (tCrushed != null)
            return new GT_Recipe(true, new ItemStack[]{aOreStack}, new ItemStack[]{GT_Utility.mul(2L, tCrushed), aOutput2, GT_OreDictUnificator.getDust(aPrefix.mSecondaryMaterial)}, null, new int[]{10000, 100 * aChance2, 1000}, null, null, 400, 2, 0);
        return null;
    }

    private static OrePrefixes prefixFromCircuitConfiguration(GT_Recipe.GT_Recipe_Map recipe_map, OrePrefixes inputPrefix, int conf) {
        switch (recipe_map.mUnlocalizedName) {
            case "gt.recipe.metalbender": {
                switch (inputPrefix) {
                    case ingot:
                        switch (conf) {
                            case 1:
                                return plate;
                            case 2:
                                return plateDouble;
                            case 3:
                                return plateTriple;
                            case 4:
                                return plateQuadruple;
                            case 5:
                                return plateQuintuple;
                            case 9:
                                return plateDense;
                            default:
                                return null;
                        }
                    case plate:
                        switch (conf) {
                            case 1:
                                return foil;
                            case 2:
                                return plateDouble;
                            case 3:
                                return plateTriple;
                            case 4:
                                return plateQuadruple;
                            case 5:
                                return plateQuintuple;
                            case 9:
                                return plateDense;
                            default:
                                return null;
                        }
                    case ingotDouble:
                        switch (conf) {
                            case 1:
                                return plateDouble;
                            case 2:
                                return plateQuintuple;
                            default:
                                return null;
                        }
                    case ingotTriple:
                        switch (conf) {
                            case 1:
                                return plateTriple;
                            case 3:
                                return plateDense;
                            default:
                                return null;
                        }
                    case ingotQuadruple:
                        return conf == 1 ? plateQuadruple : null;
                    case ingotQuintuple:
                        return conf == 1 ? plateQuintuple : null;
                    case plateDouble:
                        return conf == 2 ? plateQuadruple : null;
                    case plateTriple:
                        return conf == 3 ? plateDense : null;
                    default:
                        return null;
                }
            }
            default:
                return null;
        }
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isClientSide() && aBaseMetaTileEntity.isActive()) {
            switch (this.mSpecialEffect) {
                case 0:
                    break;
                case 1:
                    if (aBaseMetaTileEntity.getFrontFacing() != 1 && aBaseMetaTileEntity.getCoverIDAtSide((byte) 1) == 0 && !aBaseMetaTileEntity.getOpacityAtSide((byte) 1)) {
                        Random tRandom = aBaseMetaTileEntity.getWorld().rand;
                        aBaseMetaTileEntity.getWorld().spawnParticle("smoke", aBaseMetaTileEntity.getXCoord() + 0.8F - tRandom.nextFloat() * 0.6F, aBaseMetaTileEntity.getYCoord() + 0.9F + tRandom.nextFloat() * 0.2F, aBaseMetaTileEntity.getZCoord() + 0.8F - tRandom.nextFloat() * 0.6F, 0.0D, 0.0D, 0.0D);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (!super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) return false;
        if (this.mInventory[aIndex] != null) return true;
        switch (this.mInputSlotCount) {
            case 0:
                return false;
            case 1:
                return this.getFillableStack() == null ? !this.mRequiresFluidForFiltering && this.getRecipeList().containsInput(aStack) : null != this.getRecipeList().findRecipe(this.getBaseMetaTileEntity(), this.mLastRecipe, true, V[this.mTier], new FluidStack[]{this.getFillableStack()}, this.getSpecialSlot(), new ItemStack[]{aStack});
            case 2:
                return (!this.mRequiresFluidForFiltering || this.getFillableStack() != null) && (((this.getInputAt(0) != null && this.getInputAt(1) != null) || (this.getInputAt(0) == null && this.getInputAt(1) == null ? this.getRecipeList().containsInput(aStack) : (this.getRecipeList().containsInput(aStack) && null != this.getRecipeList().findRecipe(this.getBaseMetaTileEntity(), this.mLastRecipe, true, V[this.mTier], new FluidStack[]{this.getFillableStack()}, this.getSpecialSlot(), aIndex == this.getInputSlot() ? new ItemStack[]{aStack, this.getInputAt(1)} : new ItemStack[]{this.getInputAt(0), aStack})))));
            default:{
                int tID = this.getBaseMetaTileEntity().getMetaTileID();
                if (tID >= 211 && tID <= 218 || tID >= 1180 && tID <= 1187 || tID >= 10780 && tID <= 10786) {//assembler lv-iv; circuit asseblers lv - uv; assemblers luv-uev
                    if (GT_Utility.isStackValid(aStack))
                        for (int oreID : OreDictionary.getOreIDs(aStack)) {
                            if (OreDictionary.getOreName(oreID).contains("circuit"))
                                return true;
                        }
                }
                switch (this.getRecipeList().mUnlocalizedName) {
                    case "gt.recipe.metalbender":
                        return this.getRecipeList().containsInput(aStack) || GT_Utility.isStackValid(aStack) && GT_OreDictUnificator.getAssociation(aStack) != null && (Arrays.stream(PLATEBENDER_INPUT_OREPREFIXES).anyMatch(op -> op == GT_OreDictUnificator.getAssociation(aStack).mPrefix));
                }
                return this.getRecipeList().containsInput(aStack);
            }

        }
    }

    @Override
    public int checkRecipe() {
        for (GT_Recipe recipes : gt_recipes_cache) {
            if (checkRecipe(false, recipes) == FOUND_AND_SUCCESSFULLY_USED_RECIPE)
                return FOUND_AND_SUCCESSFULLY_USED_RECIPE;
        }
        GT_Recipe recipe = this.getRecipeList().findRecipe(getBaseMetaTileEntity(), mLastRecipe, false, V[mTier], new FluidStack[]{getFillableStack()}, getSpecialSlot(), getAllInputs());
        if (recipe == null) {
            recipe = abstractRecipeCalculator(this);
            if (recipe == null)
                return DID_NOT_FIND_RECIPE;
        }
        gt_recipes_cache.add(recipe);
        if (gt_recipes_cache.size() > 20)
            gt_recipes_cache.clear();
        return checkRecipe(false, recipe);
    }

    @Override
    public int checkRecipe(boolean skipOC) {
        return checkRecipe();
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeList() {
        return this.mRecipes;
    }

    @Override
    public int getCapacity() {
        return this.mTankCapacity;
    }

    @Override
    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 1 && GT_Utility.isStringValid(this.mSound)) GT_Utility.doSoundAtClient(this.mSound, 100, 1.0F, aX, aY, aZ);
    }

    @Override
    public void startProcess() {
        BaseMetaTileEntity myMetaTileEntity = ((BaseMetaTileEntity) this.getBaseMetaTileEntity());
        // Added to throttle sounds. To reduce lag, this is on the server side so BlockUpdate packets aren't sent.
        if (myMetaTileEntity.mTickTimer > (myMetaTileEntity.mLastSoundTick+ticksBetweenSounds)) {
            if (GT_Utility.isStringValid(this.mSound)) this.sendLoopStart((byte) 1);
            // Does not have overflow protection, but they are longs.
            myMetaTileEntity.mLastSoundTick = myMetaTileEntity.mTickTimer;
        }
    }

    @Override
    public FluidStack getFillableStack() {
        return this.mSharedTank ? this.getDrainableStack() : super.getFillableStack();
    }

    @Override
    public FluidStack setFillableStack(FluidStack aFluid) {
        return this.mSharedTank ? this.setDrainableStack(aFluid) : super.setFillableStack(aFluid);
    }

    @Override
    protected boolean displaysOutputFluid() {
        return !this.mSharedTank;
    }

    public enum X {PUMP, WIRE, WIRE4, HULL, PIPE, GLASS, PLATE, MOTOR, ROTOR, SENSOR, PISTON, CIRCUIT, EMITTER, CONVEYOR, ROBOT_ARM, COIL_HEATING, COIL_ELECTRIC, STICK_MAGNETIC, STICK_DISTILLATION, BETTER_CIRCUIT, FIELD_GENERATOR, COIL_HEATING_DOUBLE, STICK_ELECTROMAGNETIC}
}
