package gregtech.common.tileentities.machines.multi;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

import static gregtech.api.enums.GT_Values.ROMAN_LETTERS;

public class GT_MetaTileEntity_OreDrillingPlantTiered extends GT_MetaTileEntity_OreDrillingPlantBase {
    public GT_MetaTileEntity_OreDrillingPlantTiered(int aID, String aName, String aNameRegional, int tier) {
        super(aID, aName, aNameRegional, (byte) tier);
    }

    public GT_MetaTileEntity_OreDrillingPlantTiered(String aName, byte tier) {
        super(aName, tier);
    }

    @Override
    public String[] getDescription() {
        return getDescriptionInternal(ROMAN_LETTERS[mTier]);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_OreDrillingPlantTiered(mName, mTier);
    }

    @Override
    protected ItemList getCasingBlockItem() {
        return GT_MetaTileEntity_DrillerBase.getCasingFromTier(mTier);
    }

    @Override
    protected Materials getFrameMaterial() {
        return GT_MetaTileEntity_DrillerBase.getMaterialFromTier(mTier);
    }

    @Override
    protected int getCasingTextureIndex() {
        return GT_MetaTileEntity_DrillerBase.getTextureIndexFromTier(mTier);
    }

    @Override
    protected int getRadiusInChunks() {
        switch (mTier) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 6;
            case 4:
                return 9;
        }
        return 0;
    }

    @Override
    protected int getMinTier() {
        return mTier + 1;
    }

    @Override
    protected int getBaseProgressTime() {
        return 1120 - (160 * mTier);
    }
}