package gregtech.loaders.preload;

import gregtech.api.util.GT_Log;
import gregtech.loaders.oreprocessing.*;

public class GT_Loader_OreProcessing
        implements Runnable {
    public void run() {

        //26s all
        //arrows:  ~1s = ~5%
        GT_Log.out.println("GT_Mod: Register Ore processing.");
        new ProcessingAll();
//        new ProcessingArrows();  -> disabled due to high impact on loading and no one uses them anyways
        new ProcessingBeans();
        new ProcessingBlock();
        new ProcessingBolt();
        new ProcessingCell();
        new ProcessingCrate();
        new ProcessingCircuit();
        new ProcessingCompressed();
        new ProcessingCrafting();
        new ProcessingCrop();
        new ProcessingCrushedOre();
        new ProcessingCrystallized();

//        1/4 disabled: 25s

        new ProcessingDirty();
        new ProcessingDust();
        new ProcessingDye();
        new ProcessingFoil();
        new ProcessingFineWire();
        new ProcessingFood();
//        3/8 disabled: 9s

        new ProcessingLens();
        new ProcessingShaping();
        new ProcessingGem();
//        6/16 disabled: 24s
        new ProcessingGear();
        new ProcessingIngot(); // ~13s
        new ProcessingItem();
//          7/16 disabled: 12s
//          2/4 disabled: 8s

//        1/2 disabled : 6s
        new ProcessingLog();
        new ProcessingTransforming();
        new ProcessingNugget();
        new ProcessingOre();
        new ProcessingOrePoor();
        new ProcessingOreSmelting();
        new ProcessingPipe();
        new ProcessingPlank();
        new ProcessingPlate();
        new ProcessingWax();
        new ProcessingWire();
        new ProcessingPure();
        new ProcessingRecycling();
        new ProcessingRound();
        new ProcessingRotor();
        new ProcessingSand();
        new ProcessingSaplings();
        new ProcessingScrew();
        new ProcessingSlab();
        new ProcessingStick();
        new ProcessingStickLong();
        new ProcessingStone();
        new ProcessingStoneCobble();
        new ProcessingStoneVarious();
        new ProcessingToolHead();
        new ProcessingToolOther();

        //new ProcessingBattery();
        //new ProcessingCellPlasma();
        //new ProcessingCrushedPurified();
        //new ProcessingCrushedCentrifuged();
        //new ProcessingDustImpure();
        //new ProcessingDustSmall();
        //new ProcessingDustTiny();
        //new ProcessingGemChipped();
        //new ProcessingGemFlawed();
        //new ProcessingGemFlawless();
        //new ProcessingGemExquisite();
        //new ProcessingGearSmall();
        //new ProcessingIngot1();
        //new ProcessingIngot2();
        //new ProcessingIngot3();
        //new ProcessingIngot4();
        //new ProcessingIngot5();
        //new ProcessingIngotHot();
        //new ProcessingLeaves();
        //new ProcessingPipeSmall();
        //new ProcessingPipeMedium();
        //new ProcessingPipeLarge();
        //new ProcessingPipeHuge();
        //new ProcessingPipeTiny();
        //new ProcessingPipeRestrictive();
        //new ProcessingPlate1();
        //new ProcessingPlate2();
        //new ProcessingPlate3();
        //new ProcessingPlate4();
        //new ProcessingPlate5();
        //new ProcessingPlate9();
        //new ProcessingPlateAlloy();
        //new ProcessingToolHeadArrow();
        //new ProcessingToolHeadAxe();
        //new ProcessingToolHeadBuzzSaw();
        //new ProcessingToolHeadFile();
        //new ProcessingToolHeadHammer();
        //new ProcessingToolHeadHoe();
        //new ProcessingToolHeadPickaxe();
        //new ProcessingToolHeadSaw();
        //new ProcessingToolHeadSense();
        //new ProcessingToolHeadShovel();
        //new ProcessingToolHeadSword();
        //new ProcessingToolHeadPlow();
        //new ProcessingToolHeadDrill();
        //new ProcessingToolHeadChainsaw();
        //new ProcessingToolHeadWrench();
        //new ProcessingToolHeadUniversalSpade();

        //new ProcessingWire01();
        //new ProcessingWire02();
        //new ProcessingWire04();
        //new ProcessingWire08();
        //new ProcessingWire12();
        //new ProcessingWire16();
    }
}
