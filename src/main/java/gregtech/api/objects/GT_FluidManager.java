package gregtech.api.objects;

import com.google.common.collect.ArrayListMultimap;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IGTFluidManager;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;

public class GT_FluidManager implements IGTFluidManager {

    public static final GT_FluidManager INSTANCE = new GT_FluidManager();
    ArrayListMultimap<String, Fluid> fluidRegistry = ArrayListMultimap.create();
    HashMap<Fluid, Integer> efficencyMap = new HashMap<>();

    private GT_FluidManager() {
    }

    public static void init() {
        INSTANCE.addLubeFluid(Materials.Lubricant.getFluid(0).getFluid());

        INSTANCE.addSawingFluid(Materials.Water.getFluid(0).getFluid(), 5000);
        INSTANCE.addSawingFluid(Materials.SeedOil.getFluid(0).getFluid(), 6500);
        INSTANCE.addSawingFluid(Materials.FishOil.getFluid(0).getFluid(), 6500);
        INSTANCE.addSawingFluid(Materials.Oil.getFluid(0).getFluid(), 7000);
        INSTANCE.addSawingFluid(Materials.OilHeavy.getFluid(0).getFluid(), 7500);
        INSTANCE.addSawingFluid(Materials.OilLight.getFluid(0).getFluid(), 7500);
        INSTANCE.addSawingFluid(Materials.OilMedium.getFluid(0).getFluid(), 7500);
        INSTANCE.addSawingFluid(FluidRegistry.getFluid("liquid_extra_heavy_oil"), 9000);
        INSTANCE.addSawingFluid(Materials.Lubricant.getFluid(0).getFluid());
        INSTANCE.addSawingFluid(GT_ModHandler.getDistilledWater(0).getFluid(), 6000);

    }

    /**
     * @param aDuration duration of the base Recipe
     * @param fluid     the liquid with saws
     * @return the Time for the recipe
     */
    public static int getSawingTime(int aDuration, Fluid fluid) {
        aDuration *= 10000;
        aDuration /= GT_FluidManager.INSTANCE.getEfficencyForFluid(fluid);
        return aDuration;
    }

    /**
     * @param aDuration duration of the base Recipe
     * @param aEUt      voltage of the base Recipe
     * @param fluid     the liquid with saws
     * @return the consumed liquid
     */
    public static int getLiquidConsumptionSaw(int aDuration, int aEUt, Fluid fluid) {
        double min = Math.max(1D, 10000D / GT_FluidManager.INSTANCE.getEfficencyForFluid(fluid));
        double coeff = aDuration * aEUt;
        double divider = 1280D / min;
        double base = (1000D / Math.pow(min, -1));
        return (int) Math.ceil(Math.max(min, Math.min(base, coeff / divider)));
    }

    @Override
    public boolean addDrillingFluid(Fluid fluid) {
        return fluidRegistry.put("drilling", fluid);
    }

    @Override
    public boolean addSawingFluid(Fluid fluid) {
        return fluidRegistry.put("sawing", fluid);
    }

    @Override
    public boolean addLubeFluid(Fluid fluid) {
        return fluidRegistry.put("lube", fluid);
    }

    @Override
    public boolean addCustomFluid(Fluid fluid, String namespace) {
        return fluidRegistry.put(namespace, fluid);
    }

    @Override
    public boolean addDrillingFluid(Fluid fluid, int efficency) {
        efficencyMap.put(fluid, efficency);
        return fluidRegistry.put("drilling", fluid);
    }

    @Override
    public boolean addSawingFluid(Fluid fluid, int efficency) {
        efficencyMap.put(fluid, efficency);
        return fluidRegistry.put("sawing", fluid);
    }

    @Override
    public boolean addLubeFluid(Fluid fluid, int efficency) {
        efficencyMap.put(fluid, efficency);
        return fluidRegistry.put("lube", fluid);
    }

    @Override
    public boolean addCustomFluid(Fluid fluid, int efficency, String namespace) {
        efficencyMap.put(fluid, efficency);
        return fluidRegistry.put(namespace, fluid);
    }

    @Override
    public int getEfficencyForFluid(Fluid fluid) {
        return efficencyMap.getOrDefault(fluid, 10000);
    }

    @Override
    public Iterable<Fluid> getDrillingFluids() {
        return fluidRegistry.get("drilling");
    }

    @Override
    public Iterable<Fluid> getSawingFluids() {
        return fluidRegistry.get("sawing");
    }

    @Override
    public Iterable<Fluid> getLubeFluids() {
        return fluidRegistry.get("lube");
    }

    @Override
    public Iterable<Fluid> getCustomFluids(String namespace) {
        return fluidRegistry.get(namespace);
    }
}
