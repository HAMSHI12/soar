import { useState } from 'react';
import api from '../lib/api';

interface ActionButtonsProps {
  onActionComplete: () => void;
}

export default function ActionButtons({ onActionComplete }: ActionButtonsProps) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleGenerate = async () => {
    setLoading(true);
    setError(null);
    try {
      await api.get('/events/generate');
      alert('Events generated successfully');
      onActionComplete();
    } catch (err: any) {
      setError('Failed to generate events: ' + (err.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  const handleProcess = async () => {
    setLoading(true);
    setError(null);
    try {
      await api.get('/events/process');
      alert('Events processed successfully');
      onActionComplete();
    } catch (err: any) {
      setError('Failed to process events: ' + (err.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex space-x-4 mb-4">
      <button
        onClick={handleGenerate}
        disabled={loading}
        className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:bg-gray-400"
      >
        {loading ? 'Generating...' : 'Generate Events'}
      </button>
      <button
        onClick={handleProcess}
        disabled={loading}
        className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 disabled:bg-gray-400"
      >
        {loading ? 'Processing...' : 'Process Events'}
      </button>
      {error && <p className="text-red-500">{error}</p>}
    </div>
  );
}