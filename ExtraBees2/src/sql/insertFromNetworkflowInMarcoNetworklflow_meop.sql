INSERT INTO datavis.macro_networkflow
(Starttime,
srcESite,
destESite,
ipLayerProtocol,
SumTotalBytesSrc,
SumTotalBytesDest,
SumPacketSrc,
SumPacketDest,
SumDuration,
SumConnections)
SELECT macro_networkflow1.Starttime,
macro_networkflow1.srcESite,
macro_networkflow1.destESite,
macro_networkflow1.ipLayerProtocol,
macro_networkflow1.SumTotalBytesSrc,
macro_networkflow1.SumTotalBytesDest,
macro_networkflow1.SumPacketSrc,
macro_networkflow1.SumPacketDest,
macro_networkflow1.SumDuration,
macro_networkflow1.SumConnections FROM datavis.macro_networkflow1
WHERE NOT EXISTS (SELECT macro_networkflow.Starttime,macro_networkflow.srcESite,macro_networkflow.destESite,macro_networkflow.ipLayerProtocol
FROM datavis.macro_networkflow WHERE (macro_networkflow1.Starttime = macro_networkflow.Starttime) AND
(macro_networkflow1.srcESite = macro_networkflow.srcESite) AND
(macro_networkflow1.destESite = macro_networkflow.destESite) AND
(macro_networkflow1.ipLayerProtocol = macro_networkflow.ipLayerProtocol));
