package gregtech.api.interfaces;

import net.minecraftforge.fluids.Fluid;

public interface IGTFluidManager {

    boolean addDrillingFluid(Fluid fluid);

    boolean addSawingFluid(Fluid fluid);

    boolean addLubeFluid(Fluid fluid);

    boolean addCustomFluid(Fluid fluid, String namespace);

    boolean addDrillingFluid(Fluid fluid, int efficency);

    boolean addSawingFluid(Fluid fluid, int efficency);

    boolean addLubeFluid(Fluid fluid, int efficency);

    boolean addCustomFluid(Fluid fluid, int efficency, String namespace);

    int getEfficencyForFluid(Fluid fluid);

    Iterable<Fluid> getDrillingFluids();

    Iterable<Fluid> getSawingFluids();

    Iterable<Fluid> getLubeFluids();

    Iterable<Fluid> getCustomFluids(String namespace);

}