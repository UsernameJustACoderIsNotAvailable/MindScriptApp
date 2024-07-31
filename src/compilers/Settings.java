package compilers;

import java.util.ArrayList;
import java.util.List;

public record Settings() {
    public static final String nameSpacePrefix = "ns";
    public static final List<String> blockNames = List.of(
            "smelter", "crucible", "kiln", "press", "compressor", "press", "weaver",
            "smelter", "mixer", "mixer", "mixer", "melter", "separator", "disassembler", "press", "pulverizer",
            "incinerator", "centrifuge", "furnace", "electrolyzer", "chamber", "concentrator", "heater", "redirector",
            "incinerator", "crucible", "synthesizer", "reactor", "source", "void", "illuminator", "wall", "door",
            "mender", "projector", "dome", "mine", "thruster", "radar", "tower", "breaker", "conveyor", "distributor",
            "junction", "bridge", "sorter", "router", "gate", "driver", "duct", "unloader", "loader", "point",
            "pump", "conduit", "container", "tank", "generator", "panel", "reactor", "battery", "node", "diode",
            "condenser", "chamber", "link", "drill", "extractor", "cultivator", "crusher", "bore", "core", "vault",
            "container", "unloader", "duo", "scatter", "scorch", "hail", "arc", "wave", "lancer", "swarmer", "salvo",
            "fuse", "ripple", "cyclone", "foreshadow", "spectre", "meltdown", "segment", "parallax", "tsunami",
            "breach", "diffuse", "sublimate", "titan", "disperse", "afflict", "lustre", "scathe", "smite", "malign",
            "factory", "reconstructor", "point", "turret", "fabricator", "refabricator", "assembler", "tower", "deconstructor",
            "constructor", "loader", "message", "switch", "processor", "display", "cell", "bank", "canvas", "pad", "accelerator"
            );
    public static final List<String> numbers = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final List<String> systemWords = List.of(
            "@counter", "@unit", "@this", "@time", "@tick", "@second", "@minute", "@waveNumber", "@waveTime", "@solid", "@blockCount",
            "@mapw", "@maph", "@links", "@ipt", "@thisx", "@thisy", "true", "false"
            );
}
