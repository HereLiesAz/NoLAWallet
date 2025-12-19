package com.hereliesaz.nolawallet.util

import com.hereliesaz.nolawallet.R

/**
 * Maps the Z-Order of the license layers based on the file names provided.
 * Lower index = Bottom layer.
 */
object LicenseLayerMapper {
    val Layers = listOf(
        // 1. Backgrounds
        "assets/LA_0004_Bkgrd-copy.png",
        "assets/LA_0003_YELLOW-PATTERN.png",
        "assets/LA_0004s_0000s_0001_PATTERNS.png",
        "assets/LA_0004s_0006_PATTERNS-2.png",
        "assets/LA_0004s_0009_PATTERNS.png",
        
        // 2. Static Graphics (State Seal, etc)
        "assets/LA_0004s_0001_Louisiana.png",
        "assets/LA_0004s_0010_Building.png",
        "assets/LA_0004s_0008_CURVES-PATTERN.png",
        
        // 3. Dynamic Data Placeholders (These will be overlaid with Text, but keeping backgrounds if needed)
        // We skip the text PNGs (e.g. "JOSEPH-ROY-II") because we render real text.
        // But we keep structure lines.
        "assets/LA_0004s_0007_Ghost-lines.png",
        
        // 4. Overlays
        "assets/LA_0001_Donor-Symbol.png",
        "assets/LA_0002_MicroText.png", // Depending on resolution
        
        // 5. UV / Holograms (Topmost)
        "assets/LA_0001s_0003_UV-HOLOGRAM-MAP.png",
        "assets/LA_0001s_0001_UV-JUSTICE-----CONFIDENCE-------UNION--.png",
        "assets/LA_0001s_0002_UV-LOUISANA.png"
    )
}
