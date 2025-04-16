import { useState } from 'react';
import { DataGrid, GridColDef, GridPaginationModel } from '@mui/x-data-grid';
import { ActionResponse } from '../types';

interface EventTableProps {
  events: ActionResponse[];
  loading: boolean;
}

export default function EventTable({ events, loading }: EventTableProps) {
  const [pageSize, setPageSize] = useState(10);
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    pageSize: 10,
    page: 0,
  });

  const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'eventId', headerName: 'Event ID', width: 250 },
    { field: 'eventType', headerName: 'Event Type', width: 120 },
    { field: 'actionTaken', headerName: 'Action Taken', width: 150 },
    { field: 'details', headerName: 'Details', width: 300 },
    { field: 'sourceIp', headerName: 'Source IP', width: 150 },
    { field: 'port', headerName: 'Port', width: 100 },
    { field: 'protocol', headerName: 'Protocol', width: 120 },
    { field: 'cybersecurityDevice', headerName: 'Device', width: 150 },
  ];

  const getRowClassName = (params: any) => {
    const severity = params.row.severity?.toLowerCase();
    switch (severity) {
      case 'normal':
        return 'text-green-500';
      case 'low':
        return 'text-blue-500';
      case 'medium':
        return 'text-yellow-500';
      case 'high':
        return 'text-orange-500';
      case 'critical':
        return 'text-red-500';
      case 'system':
        return 'text-purple-500';
      case 'update':
        return 'text-gray-500';
      default:
        return 'text-black';
    }
  };

  return (
    <div className="w-full flex justify-center">
      <div style={{ height: 800, width: '100%' }}>  
        <DataGrid
          rows={events}
          columns={columns}
          loading={loading}
          pageSizeOptions={[5, 10, 20, 50, 100]}
          paginationModel={paginationModel}
          onPaginationModelChange={setPaginationModel}
          pagination
          getRowClassName={getRowClassName}
          sx={{
            '& .text-green-500': { color: 'green' },
            '& .text-blue-500': { color: 'blue' },
            '& .text-yellow-500': { color: '#DAA520' },
            '& .text-orange-500': { color: 'orange' },
            '& .text-red-500': { color: 'red' },
            '& .text-purple-500': { color: 'purple' },
            '& .text-gray-500': { color: 'gray' },
            '& .text-black': { color: 'black' },
          }}
        />
      </div>
    </div>
  );
}
